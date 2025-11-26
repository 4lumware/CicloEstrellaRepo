package com.upc.cicloestrella.controllers.application;

import com.upc.cicloestrella.DTOs.responses.dashboard.DashboardKpiResponseDTO;
import com.upc.cicloestrella.DTOs.responses.dashboard.NameValueResponseDTO;
import com.upc.cicloestrella.DTOs.responses.dashboard.RegistrationSeriesResponseDTO;
import com.upc.cicloestrella.interfaces.services.application.DashboardServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<RegistrationSeriesResponseDTO>> registrationsByMonth(@RequestParam(name = "months", defaultValue = "12") int months) {
        List<RegistrationSeriesResponseDTO> data = dashboardService.getRegistrationsByMonth(months);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/users-by-role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<NameValueResponseDTO>> usersByRole() {
        List<NameValueResponseDTO> data = dashboardService.getUsersByRole();
        return ResponseEntity.ok(data);
    }


    @GetMapping("/students-by-career")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<NameValueResponseDTO>> studentsByCareer() {
        List<NameValueResponseDTO> data = dashboardService.getStudentsByCareer();
        return ResponseEntity.ok(data);
    }

    @GetMapping("/avg-rating-per-teacher")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<NameValueResponseDTO>> avgRatingPerTeacher(@RequestParam(name = "limit", defaultValue = "20") int limit) {
        List<NameValueResponseDTO> data = dashboardService.getAvgRatingPerTeacher(limit);
        return ResponseEntity.ok(data);
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
