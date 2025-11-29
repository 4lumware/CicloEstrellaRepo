package com.upc.cicloestrella.services.application;

import com.upc.cicloestrella.DTOs.responses.dashboard.ChartDataDTO;
import com.upc.cicloestrella.DTOs.responses.dashboard.ChartDatasetDTO;
import com.upc.cicloestrella.DTOs.responses.dashboard.DashboardKpiResponseDTO;
import com.upc.cicloestrella.DTOs.responses.reviews.ReviewResponseDTO;
import com.upc.cicloestrella.entities.Review;
import com.upc.cicloestrella.interfaces.services.application.DashboardServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.application.ReviewRepository;
import com.upc.cicloestrella.repositories.interfaces.application.StudentRepository;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DashboardService implements DashboardServiceInterface {

    private final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;


    @Override
    public DashboardKpiResponseDTO getKpis(int currentDays, int previousDays) {
        DashboardKpiResponseDTO dto = new DashboardKpiResponseDTO();

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime startCurrent = now.minusDays(currentDays);
        LocalDateTime startPrev = startCurrent.minusDays(previousDays);

        LocalDate startPrevDate = startPrev.toLocalDate();
        LocalDate startCurrentDate = startCurrent.toLocalDate();
        LocalDate nowDate = now.toLocalDate();

        // --- NEW REGISTRATIONS ---
        long currentRegs = Optional.ofNullable(userRepository.countRegistrationsBetween(startCurrentDate, nowDate)).orElse(0L);

        long prevRegs = Optional.ofNullable(userRepository.countRegistrationsBetween(startPrevDate, startCurrentDate)).orElse(0L);

        double regsChange = computePercentChange(prevRegs, currentRegs);
        dto.setNewRegistrationsLast7Days(new DashboardKpiResponseDTO.KpiItem(currentRegs, regsChange, trend(regsChange)));

        // --- AVG RATING ---
        double currentRating = Optional.ofNullable(reviewRepository.findAvgRatingBetween(startCurrent, now)).orElse(BigDecimal.ZERO).doubleValue();

        double prevRating = Optional.ofNullable(reviewRepository.findAvgRatingBetween(startPrev, startCurrent)).orElse(BigDecimal.ZERO).doubleValue();

        double ratingChange = computePercentChange(prevRating, currentRating);
        dto.setAvgRatingLast30Days(new DashboardKpiResponseDTO.KpiItem(currentRating, ratingChange, trend(ratingChange)));


        // --- NEW REVIEWS ---
        long currentReviews = reviewRepository.countByCreatedAtBetween(startCurrent, now);
        long prevReviews = reviewRepository.countByCreatedAtBetween(startPrev, startCurrent);
        double reviewsChange = computePercentChange(prevReviews, currentReviews);
        dto.setNewReviewsLastNDays(new DashboardKpiResponseDTO.KpiItem(currentReviews, reviewsChange, trend(reviewsChange)));

        return dto;
    }

    private String trend(double change) {
        return change > 0 ? "up" : change < 0 ? "down" : "neutral";
    }


    private double computePercentChange(double previous, double current) {
        if (previous == 0) return current == 0 ? 0D : 100D;
        return ((current - previous) / Math.abs(previous)) * 100.0;
    }

    @Override
    public ChartDataDTO getRegistrationsByMonthChart(int months) {
        // build labels for last `months` (e.g. "2025-11")
        LocalDate now = LocalDate.now();
        List<String> labels = new ArrayList<>();
        for (int i = months - 1; i >= 0; i--) {
            labels.add(now.minusMonths(i).format(monthFormatter));
        }

        // raw: role, month, count
        List<Object[]> raw = userRepository.countRegistrationsByRolePerMonth();
        Map<String, Map<String, Long>> pivot = new LinkedHashMap<>();
        Set<String> roles = new TreeSet<>();

        for (Object[] row : raw) {
            String role = Objects.toString(row[0], "");
            String month = Objects.toString(row[1], "");
            Long cnt = row[2] == null ? 0L : ((Number) row[2]).longValue();
            roles.add(role);
            pivot.computeIfAbsent(role, k -> new HashMap<>()).put(month, cnt);
        }

        List<ChartDatasetDTO> datasets = new ArrayList<>();
        for (String role : roles) {
            List<Long> data = new ArrayList<>();
            Map<String, Long> counts = pivot.getOrDefault(role, Collections.emptyMap());
            for (String label : labels) {
                data.add(counts.getOrDefault(label, 0L));
            }
            datasets.add(new ChartDatasetDTO(role, data, null));
        }

        return new ChartDataDTO(labels, datasets);
    }

    @Override
    public ChartDataDTO getUsersByRoleChart() {
        List<Object[]> raw = userRepository.countUsersGroupByRole();
        List<String> labels = new ArrayList<>();
        List<Long> data = new ArrayList<>();
        for (Object[] r : raw) {
            labels.add(Objects.toString(r[0], ""));
            data.add(r[1] == null ? 0L : ((Number) r[1]).longValue());
        }
        ChartDatasetDTO ds = new ChartDatasetDTO("Users by Role", data, null);
        return new ChartDataDTO(labels, List.of(ds));
    }

    @Override
    public ChartDataDTO getStudentsByCareerChart() {
        List<Object[]> raw = studentRepository.countStudentsByCareer();
        List<String> labels = new ArrayList<>();
        List<Long> data = new ArrayList<>();
        for (Object[] r : raw) {
            labels.add(Objects.toString(r[0], ""));
            data.add(r[1] == null ? 0L : ((Number) r[1]).longValue());
        }
        ChartDatasetDTO ds = new ChartDatasetDTO("Students by Career", data, null);
        return new ChartDataDTO(labels, List.of(ds));
    }

    @Override
    public ChartDataDTO getAvgRatingPerTeacherChart(int limit) {
        List<Object[]> raw = reviewRepository.findAvgRatingPerTeacher(limit);
        List<String> labels = new ArrayList<>();
        List<Double> data = new ArrayList<>();
        for (Object[] r : raw) {
            String name = Objects.toString(r[1], "") + " " + Objects.toString(r[2], "");
            labels.add(name.trim());
            data.add(r[3] == null ? 0.0 : ((Number) r[3]).doubleValue());
        }
        ChartDatasetDTO ds = new ChartDatasetDTO("Avg rating per teacher", data, null);
        return new ChartDataDTO(labels, List.of(ds));
    }

    @Override
    public List<ReviewResponseDTO> getTopReviews(int limit) {
        List<Review> reviews = reviewRepository.findTopReviews(limit);
        return reviews.stream()
                .map(r -> modelMapper.map(r, ReviewResponseDTO.class))
                .toList();
    }


}
