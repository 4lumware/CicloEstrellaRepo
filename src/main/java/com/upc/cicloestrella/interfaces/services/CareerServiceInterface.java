package com.upc.cicloestrella.interfaces.services;

import com.upc.cicloestrella.DTOs.requests.CareerRequestDTO;
import com.upc.cicloestrella.DTOs.responses.CareerResponseDTO;

import java.util.List;

public interface CareerServiceInterface {
    public List<CareerResponseDTO> index();
    public CareerResponseDTO show(Long id);
    public CareerResponseDTO save(CareerRequestDTO career);
    public CareerResponseDTO update(Long id, CareerRequestDTO career);
    public void delete(Long id);
}
