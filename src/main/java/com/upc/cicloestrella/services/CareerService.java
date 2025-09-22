package com.upc.cicloestrella.services;

import com.upc.cicloestrella.DTOs.requests.CareerRequestDTO;
import com.upc.cicloestrella.DTOs.responses.CareerResponseDTO;
import com.upc.cicloestrella.entities.Career;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.interfaces.services.CareerServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.CareerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerService implements CareerServiceInterface {
    private final CareerRepository careerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CareerService(CareerRepository careerRepository , ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.careerRepository = careerRepository;
    }
    @Override
    public List<CareerResponseDTO> index() {
        return careerRepository.findAll()
                .stream()
                .map(career -> modelMapper.map(career, CareerResponseDTO.class))
                .toList();
    }

    @Override
    public CareerResponseDTO show(Long id) {
        return careerRepository.findById(id)
                .map(career -> modelMapper.map(career, CareerResponseDTO.class))
                .orElseThrow(() -> new EntityIdNotFoundException("Carrera con id " + id + " no encontrada"));
    }

    @Override
    public CareerResponseDTO save(CareerRequestDTO career) {
        Career careerEntity = modelMapper.map(career, Career.class);

        Career savedCareer = careerRepository.save(careerEntity);
        return modelMapper.map(savedCareer, CareerResponseDTO.class);
    }

    @Override
    public CareerResponseDTO update(Long id, CareerRequestDTO career) {
        return careerRepository.findById(id)
                .map(existingCareer -> {
                    modelMapper.map(career, existingCareer);
                    Career updatedCareer = careerRepository.save(existingCareer);
                    return modelMapper.map(updatedCareer, CareerResponseDTO.class);
                })
                .orElseThrow(() -> new EntityIdNotFoundException("Carrera con id " + id + " no encontrada"));
    }

    @Override
    public void delete(Long id) {
        if (!careerRepository.existsById(id)) {
            throw new EntityIdNotFoundException("Carrera con id " + id + " no encontrada");
        }
        careerRepository.deleteById(id);
    }
}
