package com.upc.cicloestrella.DTOs.responses.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class RegistrationSeriesResponseDTO {
    private String name;
    private List<DataPoint> series;

    @AllArgsConstructor
    @Getter
    @Setter
    public static class DataPoint {
        private String name;
        private long value;

    }
}

