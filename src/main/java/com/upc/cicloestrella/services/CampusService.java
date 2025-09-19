package com.upc.cicloestrella.services;

import com.upc.cicloestrella.DTOs.CampusRequestDTO;
import com.upc.cicloestrella.DTOs.CampusResponseDTO;
import com.upc.cicloestrella.entities.Campus;
import com.upc.cicloestrella.interfaces.services.CampusServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.CampusRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampusService implements CampusServiceInterface {
    private final CampusRepository campusRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public CampusService(CampusRepository campusRepository, ModelMapper modelMapper) {
        this.campusRepository = campusRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<CampusResponseDTO> index() {
        return campusRepository.findAll()
                .stream()
                .map(campus -> modelMapper.map(campus, CampusResponseDTO.class))
                .toList();

    }

    @Override
    public CampusResponseDTO show(Long id) {
        return campusRepository.findById(id)
                .map(campus -> modelMapper.map(campus, CampusResponseDTO.class))
                .orElse(null);
    }

    @Override
    public CampusResponseDTO save(CampusRequestDTO campus) {
        Campus campusEntity = modelMapper.map(campus, Campus.class);

        Campus savedCampus = campusRepository.save(campusEntity);
        return modelMapper.map(savedCampus, CampusResponseDTO.class);
    }

    @Override
    public CampusResponseDTO update(Long id, CampusRequestDTO campus) {
        return campusRepository.findById(id)
                .map(existingCampus -> {
                    modelMapper.map(campus, existingCampus);
                    Campus updatedCampus = campusRepository.save(existingCampus);
                    return modelMapper.map(updatedCampus, CampusResponseDTO.class);
                })
                .orElse(null);
    }

    @Override
    public void delete(Long id) {
        campusRepository.deleteById(id);
    }
}
