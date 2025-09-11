package com.upc.cicloestrella.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class CourseResponseDTO {

    private String name;
    private String description;
    private List<FormatResponseDTO> formats;
}
