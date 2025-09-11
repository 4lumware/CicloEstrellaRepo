package com.upc.cicloestrella.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CareerRequestDTO {

    @NotBlank(message = "El nombre de la carrera no puede estar vacío")
    @Size(max = 150, message = "El nombre de la carrera no debe superar los 150 caracteres")
    private String careerName;
}
