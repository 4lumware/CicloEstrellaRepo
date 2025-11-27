package com.upc.cicloestrella.interfaces.services.application;


import com.upc.cicloestrella.DTOs.requests.FormalityRequestDTO;
import com.upc.cicloestrella.DTOs.responses.FormalityResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface FormalityServiceInterface {
    FormalityResponseDTO findById(Long idFormality);

    Page<FormalityResponseDTO> findAll(String title, String description, LocalDateTime from, LocalDateTime to, Pageable pageable);

    FormalityResponseDTO insert(FormalityRequestDTO formalityDTO);

    FormalityResponseDTO update(FormalityRequestDTO formalityRequestDTO , Long idFormality);

    void delete(Long idFormality);

}
