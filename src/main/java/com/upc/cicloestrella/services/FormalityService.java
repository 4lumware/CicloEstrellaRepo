package com.upc.cicloestrella.services;


import com.upc.cicloestrella.DTOs.FormalityDTO;
import com.upc.cicloestrella.entities.Formality;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.interfaces.services.FormalityServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.FormalityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return formalityRepository.findById(idFormality)
                .map(formality -> modelMapper.map(formality, FormalityDTO.class))
                .orElseThrow(() -> new EntityIdNotFoundException("Formalidad con id " + idFormality + " no encontrada"));
    }

    @Override
    public List<FormalityDTO> findAll() {
        return formalityRepository.findAll()
                .stream()
                .map(formality -> modelMapper.map(formality, FormalityDTO.class))
                .collect(Collectors.toList());
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
    public Formality update(Formality formality) {
        if (!formalityRepository.existsById(formality.getIdFormality())) {
            throw new EntityIdNotFoundException("Formalidad con id " + formality.getIdFormality() + " no encontrada");
        }
        return formalityRepository.save(formality); //update
    }

    @Override
    public void delete(Long idFormality) {
        if (!formalityRepository.existsById(idFormality)) {
            throw new EntityIdNotFoundException("Formalidad con id " + idFormality + " no encontrada");
        }
        formalityRepository.deleteById(idFormality);
    }
}
