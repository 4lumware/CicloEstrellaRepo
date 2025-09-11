package com.upc.cicloestrella.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class CourseResponseDTO {

    private String courseName;
    private List<FormatResponseDTO> formats;
}
