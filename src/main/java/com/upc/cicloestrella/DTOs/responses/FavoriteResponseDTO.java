package com.upc.cicloestrella.DTOs.responses;

import com.upc.cicloestrella.entities.Favorite;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FavoriteResponseDTO {
    private Long id;
    private Favorite.FavoriteType type;
    private Object favorite;
}
