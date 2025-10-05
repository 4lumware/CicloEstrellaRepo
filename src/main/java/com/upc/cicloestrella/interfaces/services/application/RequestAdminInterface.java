package com.upc.cicloestrella.interfaces.services.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.upc.cicloestrella.DTOs.responses.RequestContentResponseDTO;

public interface RequestAdminInterface {
    RequestContentResponseDTO acceptRequest(Long requestId) throws JsonProcessingException;
    RequestContentResponseDTO rejectRequest(Long requestId) throws JsonProcessingException;
}
