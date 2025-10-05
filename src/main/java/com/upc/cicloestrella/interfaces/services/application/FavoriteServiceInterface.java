package com.upc.cicloestrella.interfaces.services.application;

import com.upc.cicloestrella.DTOs.requests.FavoriteRequestDTO;
import com.upc.cicloestrella.DTOs.responses.FavoriteResponseDTO;

import java.util.List;

public interface FavoriteServiceInterface  {
    FavoriteResponseDTO save(Long studentId ,  FavoriteRequestDTO favoriteRequestDTO);
    List<FavoriteResponseDTO> index(Long studentId);
    FavoriteResponseDTO findById(Long studentId , Long favoriteId);
    FavoriteResponseDTO delete(Long studentId,    Long favoriteId);
}
