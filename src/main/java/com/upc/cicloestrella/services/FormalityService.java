package com.upc.cicloestrella.services;


import com.upc.cicloestrella.DTOs.FormalityDTO;
import com.upc.cicloestrella.entities.Formality;
import com.upc.cicloestrella.interfaces.services.FormalityServiceInterface;
import com.upc.cicloestrella.repositories.FormalityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormalityService implements FormalityServiceInterface {
    @Autowired
    public FormalityRepository formalityRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Formality findById(Long idFormality) {
        return formalityRepository.findById(idFormality).get();
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
        if (formalityRepository.existsById(formality.getIdFormality())) {
            return formalityRepository.save(formality); //update
        }
        return null;
    }

    @Override
    public void delete(Long idFormality) {
        formalityRepository.deleteById(idFormality);
    }

}
