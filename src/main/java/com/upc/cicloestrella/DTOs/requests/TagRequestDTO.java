package com.upc.cicloestrella.DTOs.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TagRequestDTO {
    @NotBlank(message = "El nombre de la etiqueta no puede estar vacío")
    @Size(max = 50, message = "El nombre de la etiqueta no debe superar los 50 caracteres")
    private String tagName;
}
