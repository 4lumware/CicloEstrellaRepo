package com.upc.cicloestrella.DTOs.requests;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FormalityRequestDTO {
    @NotNull(message = "El título no puede ser nulo")
    private String title;
    @NotNull(message = "La descripción no puede ser nula")
    private String description;
    @NotNull(message = "La fecha de inicio no puede ser nula")
    private LocalDate startDate;
    @NotNull(message = "La fecha de fin no puede ser nula")
    private LocalDate endDate;
}
