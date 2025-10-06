package com.upc.cicloestrella.DTOs.requests;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class CommentRequestDTO {

    @NotBlank(message = "El mensaje del comentario no puede ser nulo")
    private String text;
}
