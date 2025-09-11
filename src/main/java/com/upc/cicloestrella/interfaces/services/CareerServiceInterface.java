package com.upc.cicloestrella.interfaces.services;

import com.upc.cicloestrella.DTOs.CareerRequestDTO;
import com.upc.cicloestrella.DTOs.CareerResponseDTO;

import java.util.List;

public interface CareerServiceInterface {
    public List<CareerResponseDTO> index();
    public CareerResponseDTO show(Long id);
    public CareerResponseDTO save(CareerRequestDTO career);
    public CareerResponseDTO update(Long id, CareerRequestDTO career);
    public void delete(Long id);
}
