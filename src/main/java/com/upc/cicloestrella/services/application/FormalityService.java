package com.upc.cicloestrella.services.application;


import com.upc.cicloestrella.DTOs.requests.FormalityRequestDTO;
import com.upc.cicloestrella.DTOs.responses.FormalityResponseDTO;
import com.upc.cicloestrella.entities.Formality;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.interfaces.services.application.FormalityServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.application.FormalityRepository;
import com.upc.cicloestrella.specifications.application.FormalitySpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FormalityService implements FormalityServiceInterface {
    private final FormalityRepository formalityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FormalityService(FormalityRepository formalityRepository, ModelMapper modelMapper) {
        this.formalityRepository = formalityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public FormalityResponseDTO findById(Long idFormality) {
        return formalityRepository.findById(idFormality).map(formality -> modelMapper.map(formality, FormalityResponseDTO.class)).orElseThrow(() -> new EntityIdNotFoundException("Formalidad con id " + idFormality + " no encontrada"));
    }

    @Override
    public Page<FormalityResponseDTO> findAll(String title, String description, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        Specification<Formality> formalities = FormalitySpecification.build(title, description, from, to);
        Page<Formality> formalityPage = formalityRepository.findAll(formalities, pageable);
        return formalityPage.map(formality -> modelMapper.map(formality, FormalityResponseDTO.class));
    }


    @Override
    public FormalityResponseDTO insert(FormalityRequestDTO formalityDTO) {
        Formality formality = modelMapper.map(formalityDTO, Formality.class);
        Formality savedFormality = formalityRepository.save(formality);
        return modelMapper.map(savedFormality, FormalityResponseDTO.class);
    }

    @Override
    public FormalityResponseDTO update(FormalityRequestDTO formalityRequestDTO, Long idFormality) {
        Formality formality = formalityRepository.findById(idFormality).orElseThrow(() -> new EntityIdNotFoundException("Formalidad con id " + idFormality + " no encontrada"));
        modelMapper.map(formalityRequestDTO , formality);
        Formality updatedFormality = formalityRepository.save(formality);
        return modelMapper.map(updatedFormality, FormalityResponseDTO.class);
    }

    @Override
    public void delete(Long idFormality) {
        if (!formalityRepository.existsById(idFormality)) {
            throw new EntityIdNotFoundException("Formalidad con id " + idFormality + " no encontrada");
        }
        formalityRepository.deleteById(idFormality);
    }
}
