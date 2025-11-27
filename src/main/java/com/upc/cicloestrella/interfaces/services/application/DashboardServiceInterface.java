package com.upc.cicloestrella.interfaces.services.application;

import com.upc.cicloestrella.DTOs.responses.dashboard.ChartDataDTO;
import com.upc.cicloestrella.DTOs.responses.dashboard.DashboardKpiResponseDTO;



public interface DashboardServiceInterface {


    ChartDataDTO getRegistrationsByMonthChart(int months);
    ChartDataDTO getUsersByRoleChart();
    ChartDataDTO getStudentsByCareerChart();
    ChartDataDTO getAvgRatingPerTeacherChart(int limit);

    DashboardKpiResponseDTO getKpis(int currentDays, int previousDays);
}
