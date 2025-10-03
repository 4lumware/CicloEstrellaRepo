package com.upc.cicloestrella.interfaces.services.application;

import com.upc.cicloestrella.DTOs.requests.FavoriteRequestDTO;
import com.upc.cicloestrella.DTOs.responses.FavoriteResponseDTO;

import java.util.List;

public interface FavoriteServiceInterface  {
    FavoriteResponseDTO save(FavoriteRequestDTO favoriteRequestDTO);
    List<FavoriteResponseDTO> index();
    FavoriteResponseDTO findById(Long favoriteId);
    FavoriteResponseDTO delete(Long favoriteId);

}
