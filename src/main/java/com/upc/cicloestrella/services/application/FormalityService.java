package com.upc.cicloestrella.services.application;


import com.upc.cicloestrella.DTOs.FormalityDTO;
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
    public FormalityDTO findById(Long idFormality) {
        return formalityRepository.findById(idFormality).map(formality -> modelMapper.map(formality, FormalityDTO.class)).orElseThrow(() -> new EntityIdNotFoundException("Formalidad con id " + idFormality + " no encontrada"));
    }

    @Override
    public Page<FormalityDTO> findAll(String title, String description, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        Specification<Formality> formalities = FormalitySpecification.build(title, description, from, to);
        Page<Formality> formalityPage = formalityRepository.findAll(formalities, pageable);
        return formalityPage.map(formality -> modelMapper.map(formality, FormalityDTO.class));
    }


    @Override
    public FormalityDTO insert(FormalityDTO formalityDTO) {
        if (formalityDTO.getIdFormality() == null) {
            Formality formality = modelMapper.map(formalityDTO, Formality.class);
            formality = formalityRepository.save(formality);
            return modelMapper.map(formality, FormalityDTO.class);
        }
        return null;
    }

    @Override
    public FormalityDTO update(Formality formality) {
        if (!formalityRepository.existsById(formality.getId())) {
            throw new EntityIdNotFoundException("Formalidad con id " + formality.getId() + " no encontrada");
        }
        Formality updatedFormality = formalityRepository.save(formality);
        return modelMapper.map(updatedFormality, FormalityDTO.class);
    }

    @Override
    public void delete(Long idFormality) {
        if (!formalityRepository.existsById(idFormality)) {
            throw new EntityIdNotFoundException("Formalidad con id " + idFormality + " no encontrada");
        }
        formalityRepository.deleteById(idFormality);
    }
}
