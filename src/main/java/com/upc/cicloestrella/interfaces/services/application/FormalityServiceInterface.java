package com.upc.cicloestrella.interfaces.services.application;


import com.upc.cicloestrella.DTOs.FormalityDTO;
import com.upc.cicloestrella.entities.Formality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface FormalityServiceInterface {
    FormalityDTO findById(Long idFormality);

    Page<FormalityDTO> findAll(String title, String description, LocalDateTime from, LocalDateTime to, Pageable pageable);

    FormalityDTO insert(FormalityDTO formalityDTO);

    FormalityDTO update(Formality formality);

    void delete(Long idFormality);

}
