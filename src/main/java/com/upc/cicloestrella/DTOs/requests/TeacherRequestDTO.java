package com.upc.cicloestrella.DTOs.requests;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class TeacherRequestDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    private String firstName;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 100, message = "El apellido no debe superar los 100 caracteres")
    private String lastName;

    @Size(max = 1000, message = "La descripción no debe superar los 1000 caracteres")
    private String generalDescription;

    @NotNull(message = "La foto de perfil no puede ser nula")
    private String profilePictureUrl;

    @NotEmpty(message = "Debe seleccionar al menos un campus")
    private List<Long> campusIds;

    @NotEmpty(message = "Debe seleccionar al menos una carrera")
    private List<Long> careerIds;

    @NotEmpty(message = "Debe seleccionar al menos un curso")
    private List<Long> courseIds;
}
