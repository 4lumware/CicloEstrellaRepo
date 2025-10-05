package com.upc.cicloestrella.interfaces.services.application;

import com.upc.cicloestrella.DTOs.requests.RequestContentRequestDTO;
import com.upc.cicloestrella.DTOs.responses.RequestContentResponseDTO;

import java.util.List;

public interface RequestServiceInterface {
    public RequestContentResponseDTO save(Long studentId,RequestContentRequestDTO requestContentRequestDTO);
    public RequestContentResponseDTO delete(Long studentId , Long requestId);
    public RequestContentResponseDTO show(Long studentId , Long requestId);
    public List<RequestContentResponseDTO> findById(Long studentId);
    public List<RequestContentResponseDTO> index();
}
