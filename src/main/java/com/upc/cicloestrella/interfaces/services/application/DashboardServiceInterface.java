package com.upc.cicloestrella.interfaces.services.application;

import com.upc.cicloestrella.DTOs.responses.dashboard.DashboardKpiResponseDTO;
import com.upc.cicloestrella.DTOs.responses.dashboard.NameValueResponseDTO;
import com.upc.cicloestrella.DTOs.responses.dashboard.RegistrationSeriesResponseDTO;

import java.util.List;

public interface DashboardServiceInterface {
    List<RegistrationSeriesResponseDTO> getRegistrationsByMonth(int months);
    List<NameValueResponseDTO> getUsersByRole();
    List<NameValueResponseDTO> getStudentsByCareer();
    List<NameValueResponseDTO> getAvgRatingPerTeacher(int limit);
    DashboardKpiResponseDTO getKpis(int currentDays, int previousDays);
}

