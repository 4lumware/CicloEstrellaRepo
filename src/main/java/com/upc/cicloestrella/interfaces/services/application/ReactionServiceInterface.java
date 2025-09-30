package com.upc.cicloestrella.interfaces.services.application;

import com.upc.cicloestrella.DTOs.requests.ReactionRequestDTO;
import com.upc.cicloestrella.DTOs.responses.ReactionResponseDTO;
import java.util.List;

public interface ReactionServiceInterface {
    ReactionResponseDTO save(ReactionRequestDTO reactionRequestDTO);
    List<ReactionResponseDTO> index(String reactionName);
    ReactionResponseDTO show(Long id);
    ReactionResponseDTO update(Long id, ReactionRequestDTO reactionRequestDTO);
    void delete(Long id);
}
