package com.upc.cicloestrella.DTOs.responses.dashboard;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardKpiResponseDTO {
    private KpiItem newRegistrationsLast7Days;
    private KpiItem avgRatingLast30Days;
    private KpiItem newReviewsLastNDays; // nuevo KPI

    @Setter
    @Getter
    @AllArgsConstructor
    public static class KpiItem {
        private double value;
        private double changePercent;
        private String trend;
    }
}
