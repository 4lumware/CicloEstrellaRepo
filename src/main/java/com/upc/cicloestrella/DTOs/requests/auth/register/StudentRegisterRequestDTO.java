package com.upc.cicloestrella.DTOs.requests.auth.register;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudentRegisterRequestDTO extends UserRegisterRequestDTO {

    @NotNull(message = "El semestre actual no puede estar vacío")
    @Min(value = 1, message = "El semestre actual debe ser al menos 1")
    @Max(value = 20, message = "El semestre actual no debe superar 20")

    private int currentSemester;

    @NotNull(message = "La lista de carreras no puede ser null")
    @Size(min = 1, message = "Debe seleccionar al menos una carrera")
    private List<@Positive(message = "El ID de la carrera debe ser positivo") Long> careerIds;
}
