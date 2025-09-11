package com.upc.cicloestrella.DTOs;

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

    @NotBlank(message = "La descripción del curso no puede estar vacía")
    @Size(max = 1000, message = "La descripción no debe superar los 1000 caracteres")
    private String description;

    @NotEmpty(message = "Debe asignar al menos un formato al curso")
    private List<Long> formatsIds;
}
