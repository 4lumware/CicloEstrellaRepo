package com.upc.cicloestrella.DTOs.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CourseRequestDTO {
    @NotBlank(message = "El nombre del curso no puede estar vacío")
    @Size(max = 150, message = "El nombre del curso no debe superar los 150 caracteres")
    private String courseName;

    @NotEmpty(message = "Debe asignar al menos un formato al curso")
    private List<Long> formatsIds;
}
