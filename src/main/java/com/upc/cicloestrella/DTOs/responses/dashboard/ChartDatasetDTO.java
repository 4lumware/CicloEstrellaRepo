package com.upc.cicloestrella.DTOs.responses.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChartDatasetDTO {
    private String label;
    private List<? extends Number> data;
    private String backgroundColor; // optional
}
