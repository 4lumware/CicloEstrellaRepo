package com.upc.cicloestrella.DTOs.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ReactionRequestDTO {
    @NotBlank(message = "El nombre de la reacción es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String reactionName;

    @NotBlank(message = "El icono de la reacción es obligatorio")
    @Size(max = 255, message = "El icono no puede exceder 255 caracteres")
    private String icon_url;

}
