package com.upc.cicloestrella.interfaces.services.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.upc.cicloestrella.DTOs.requests.RequestContentRequestDTO;
import com.upc.cicloestrella.DTOs.responses.RequestContentResponseDTO;

import java.util.List;

public interface RequestServiceInterface {
    RequestContentResponseDTO save(Long studentId,RequestContentRequestDTO requestContentRequestDTO) throws JsonProcessingException;
    RequestContentResponseDTO delete(Long studentId, Long requestId) throws JsonProcessingException;
    RequestContentResponseDTO show(Long studentId , Long requestId) throws JsonProcessingException;
    RequestContentResponseDTO findById(Long requestId) throws JsonProcessingException;
    List<RequestContentResponseDTO> allByStudentId(Long studentId);
    List<RequestContentResponseDTO> index();
}
