package com.upc.cicloestrella.services.application;

import com.upc.cicloestrella.DTOs.responses.dashboard.DashboardKpiResponseDTO;
import com.upc.cicloestrella.DTOs.responses.dashboard.NameValueResponseDTO;
import com.upc.cicloestrella.DTOs.responses.dashboard.RegistrationSeriesResponseDTO;
import com.upc.cicloestrella.interfaces.services.application.DashboardServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.application.ReviewRepository;
import com.upc.cicloestrella.repositories.interfaces.application.StudentRepository;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import lombok.RequiredArgsConstructor;
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
    private final StudentRepository studentRepository;
    private final ReviewRepository reviewRepository;


    @Override
    public List<RegistrationSeriesResponseDTO> getRegistrationsByMonth(int months) {
        LocalDate now = LocalDate.now();
        List<String> labels = new ArrayList<>();
        for (int i = months - 1; i >= 0; i--) {
            labels.add(now.minusMonths(i).format(monthFormatter));
        }

        List<Object[]> raw = userRepository.countRegistrationsByRolePerMonth();
        Map<String, Map<String, Long>> pivot = new HashMap<>();
        Set<String> roles = new TreeSet<>();

        raw.forEach(row -> {
            String role = Objects.toString(row[0], "");
            String month = Objects.toString(row[1], "");
            Long count = row[2] == null ? 0L : ((Number) row[2]).longValue();

            roles.add(role);
            pivot.computeIfAbsent(role, k -> new HashMap<>()).put(month, count);
        });

        List<RegistrationSeriesResponseDTO> out = new ArrayList<>();
        for (String role : roles) {
            List<RegistrationSeriesResponseDTO.DataPoint> series = new ArrayList<>();
            Map<String, Long> counts = pivot.getOrDefault(role, Collections.emptyMap());
            for (String label : labels) {
                series.add(new RegistrationSeriesResponseDTO.DataPoint(label, counts.getOrDefault(label, 0L)));
            }
            out.add(new RegistrationSeriesResponseDTO(role, series));
        }
        return out;
    }

    @Override
    public List<NameValueResponseDTO> getUsersByRole() {
        List<Object[]> raw = userRepository.countUsersGroupByRole();
        return getNameValueResponseDTOS(raw);

    }

    private List<NameValueResponseDTO> getNameValueResponseDTOS(List<Object[]> raw) {
        return raw.stream().filter(r -> r[0] != null).map(r -> {
            String name = Objects.toString(r[0], "");
            double cnt = r[1] == null ? 0D : ((Number) r[1]).doubleValue();
            return new NameValueResponseDTO(name, cnt);
        }).toList();
    }

    @Override
    public List<NameValueResponseDTO> getStudentsByCareer() {
        List<Object[]> raw = studentRepository.countStudentsByCareer();
        return getNameValueResponseDTOS(raw);
    }

    @Override
    public List<NameValueResponseDTO> getAvgRatingPerTeacher(int limit) {
        List<Object[]> raw = reviewRepository.findAvgRatingPerTeacher(limit);

        return raw.stream().map(r -> {
            Long teacherId = ((Number) r[0]).longValue();
            String name = r[1] + " " + r[2];
            double avg = r[3] == null ? 0 : ((Number) r[3]).doubleValue();
            return new NameValueResponseDTO(name, avg);
        }).toList();
    }

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
}
