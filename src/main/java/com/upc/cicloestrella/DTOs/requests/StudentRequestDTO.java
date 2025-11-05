package com.upc.cicloestrella.DTOs.requests;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class StudentRequestDTO {

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 50, message = "El nombre de usuario no debe superar los 50 caracteres")
    private String username;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "Debe ser un correo electrónico válido")
    private String email;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotNull(message = "El nombre no puede estar vacío")
    private String profilePictureUrl;

    @NotNull(message = "El semestre actual no puede estar vacío")
    @Min(value = 1, message = "El semestre actual debe ser al menos 1")
    @Max(value = 20, message = "El semestre actual no debe superar 20")
    private int currentSemester;

    @NotNull(message = "La lista de carreras no puede ser null")
    @Size(min = 1, message = "Debe seleccionar al menos una carrera")
    private List<@Positive(message = "El ID de la carrera debe ser positivo") Long> careerIds;

}
