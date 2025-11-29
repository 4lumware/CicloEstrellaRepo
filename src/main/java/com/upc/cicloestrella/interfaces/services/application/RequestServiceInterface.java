package com.upc.cicloestrella.interfaces.services.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.upc.cicloestrella.DTOs.requests.RequestContentRequestDTO;
import com.upc.cicloestrella.DTOs.responses.RequestContentResponseDTO;
import com.upc.cicloestrella.entities.Request;
import com.upc.cicloestrella.enums.RequestTypeEnum;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface RequestServiceInterface {
    RequestContentResponseDTO save(Long studentId,RequestContentRequestDTO requestContentRequestDTO) throws JsonProcessingException;
    RequestContentResponseDTO delete(Long studentId, Long requestId) throws JsonProcessingException;
    RequestContentResponseDTO show(Long studentId , Long requestId) throws JsonProcessingException;
    RequestContentResponseDTO findById(Long requestId) throws JsonProcessingException;
    Page<RequestContentResponseDTO> allByStudentId(LocalDateTime startDate , LocalDateTime endDate , String teacherName , Long courseId , Long campusId ,  Long studentId , int page , int size);
    Page<RequestContentResponseDTO> index(Request.RequestStatus status , RequestTypeEnum type , String studentName , LocalDateTime startDate , LocalDateTime endDate , String teacherName , Long courseId ,Long campusId ,  int page , int size);
}
