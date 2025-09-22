package com.upc.cicloestrella.DTOs.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CampusRequestDTO {
    @NotBlank(message = "El nombre del campus no puede estar vacío")
    @Size(max = 150, message = "El nombre del campus no debe superar los 150 caracteres")
    private String campusName;
}

