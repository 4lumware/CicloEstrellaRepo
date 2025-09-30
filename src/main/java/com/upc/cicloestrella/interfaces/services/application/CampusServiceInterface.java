package com.upc.cicloestrella.interfaces.services.application;

import com.upc.cicloestrella.DTOs.requests.CampusRequestDTO;
import com.upc.cicloestrella.DTOs.responses.CampusResponseDTO;

import java.util.List;

public interface CampusServiceInterface {
    public List<CampusResponseDTO> index();
    public CampusResponseDTO show(Long id);
    public CampusResponseDTO save(CampusRequestDTO campus);
    public CampusResponseDTO update(Long id, CampusRequestDTO campus);
    public void delete(Long id);
}
