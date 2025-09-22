package com.upc.cicloestrella.DTOs.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FormatRequestDTO {

    @NotBlank(message = "El nombre del formato no puede estar vacío")
    @Size(max = 100, message = "El nombre del formato no debe superar los 100 caracteres")
    private String formatName;

}
