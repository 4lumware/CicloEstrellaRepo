package com.upc.cicloestrella.DTOs.requests;


import com.upc.cicloestrella.entities.Favorite;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoriteRequestDTO {
    @NotNull(message = "El tipo de favorito no puede ser nulo")
    private Favorite.FavoriteType type;
    @NotBlank(message = "La nota de favorito no puede estar vacía")
    private String note;
    @NotNull(message = "El ID de referencia no puede ser nulo")
    private Long referenceId;
}

