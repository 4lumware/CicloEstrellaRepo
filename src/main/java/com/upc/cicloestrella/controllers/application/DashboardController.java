package com.upc.cicloestrella.controllers.application;

import com.upc.cicloestrella.DTOs.responses.dashboard.ChartDataDTO;
import com.upc.cicloestrella.DTOs.responses.dashboard.DashboardKpiResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.application.DashboardServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardServiceInterface dashboardService;

    @Autowired
    public DashboardController(DashboardServiceInterface dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/registrations-by-month")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ChartDataDTO>> registrationsByMonth(@RequestParam(name = "months", defaultValue = "12") int months) {
        ChartDataDTO data = dashboardService.getRegistrationsByMonthChart(months);
        return ResponseEntity.ok(ApiResponse.<ChartDataDTO>builder()
                .data(data)
                .message("Registrations by month chart data retrieved successfully")
                .status(200)
                .build());
    }

    @GetMapping("/users-by-role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ChartDataDTO>> usersByRole() {
        ChartDataDTO data = dashboardService.getUsersByRoleChart();
        return ResponseEntity.ok(ApiResponse.<ChartDataDTO>builder()
                .data(data)
                .message("Users by role chart data retrieved successfully")
                .status(200)
                .build());
    }


    @GetMapping("/students-by-career")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ChartDataDTO>> studentsByCareer() {
        ChartDataDTO data = dashboardService.getStudentsByCareerChart();
        return ResponseEntity.ok(ApiResponse.<ChartDataDTO>builder()
                .data(data)
                .message("Students by career chart data retrieved successfully")
                .status(200)
                .build());
    }

    @GetMapping("/avg-rating-per-teacher")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ChartDataDTO>> avgRatingPerTeacher(@RequestParam(name = "limit", defaultValue = "20") int limit) {
        ChartDataDTO data = dashboardService.getAvgRatingPerTeacherChart(limit);
        return ResponseEntity.ok(ApiResponse.<ChartDataDTO>builder()
                .data(data)
                .message("Average rating per teacher chart data retrieved successfully")
                .status(200)
                .build());
    }

    @GetMapping("/kpis")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DashboardKpiResponseDTO> kpis(
            @RequestParam(name = "currentDays", defaultValue = "30") int currentDays,
            @RequestParam(name = "previousDays", defaultValue = "30") int previousDays
    ) {
        DashboardKpiResponseDTO dto = dashboardService.getKpis(currentDays, previousDays);
        return ResponseEntity.ok(dto);
    }

}
