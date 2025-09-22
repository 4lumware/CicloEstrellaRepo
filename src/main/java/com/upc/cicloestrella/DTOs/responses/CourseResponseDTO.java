package com.upc.cicloestrella.DTOs.responses;

import lombok.Data;

import java.util.List;

@Data
public class CourseResponseDTO {
    private Long id;
    private String courseName;
    private List<FormatResponseDTO> formats;
}
