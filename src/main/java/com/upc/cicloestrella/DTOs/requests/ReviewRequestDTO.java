package com.upc.cicloestrella.DTOs.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDTO {

    @NotNull(message = "El identificador del estudiante es requerido")
    private Long studentId;

    @NotNull(message = "El identificador del profesor es requerido")
    private Long teacherId;

    private List<Long> reactionIds;
    private List<Long> tagIds;

    @NotBlank(message = "La descripción es requerida")
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String description;

    @NotNull(message = "El rating es requerido")
    @DecimalMin(value = "0.00", message = "El rating debe ser mayor o igual a 0")
    @DecimalMax(value = "10.00", message = "El rating debe ser menor o igual a 10")
    private BigDecimal rating;

}
