package com.upc.cicloestrella.DTOs.requests;


import com.upc.cicloestrella.entities.Favorite;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoriteRequestDTO {
    @NotNull(message = "El tipo de favorito no puede ser nulo")
    private Favorite.FavoriteType type;
    @NotNull(message = "El ID de referencia no puede ser nulo")
    private Long referenceId;
}
