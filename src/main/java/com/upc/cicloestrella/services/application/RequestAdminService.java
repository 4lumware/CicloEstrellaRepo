package com.upc.cicloestrella.services.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upc.cicloestrella.DTOs.responses.RequestContentResponseDTO;
import com.upc.cicloestrella.DTOs.responses.StudentResponseDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherJsonResponseConversionDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherResponseDTO;
import com.upc.cicloestrella.entities.Request;
import com.upc.cicloestrella.entities.Teacher;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.interfaces.services.application.RequestAdminInterface;
import com.upc.cicloestrella.mappers.RequestMapper;
import com.upc.cicloestrella.mappers.StudentMapper;
import com.upc.cicloestrella.repositories.interfaces.application.RequestRepository;
import com.upc.cicloestrella.repositories.logic.application.TeacherApplicationJsonRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestAdminService implements RequestAdminInterface {
    private final TeacherApplicationJsonRepository teacherApplicationJsonRepository;
    private final RequestRepository requestRepository;
    private final StudentMapper studentMapper;
    private final RequestMapper requestMapper;
    private final ModelMapper modelMapper;

    @Override
    public RequestContentResponseDTO acceptRequest(Long requestId) throws JsonProcessingException {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new EntityIdNotFoundException("No se encontro la solicitud con id: " + requestId));
        Teacher teacher = teacherApplicationJsonRepository.save(request.getContent());
        requestRepository.delete(request);
        TeacherResponseDTO teacherDTO = modelMapper.map(teacher, TeacherResponseDTO.class);
        StudentResponseDTO studentDTO = studentMapper.toDTO(request.getStudent());
        return RequestContentResponseDTO
                .builder()
                .id(teacher.getId())
                .student(studentDTO)
                .requestType(request.getRequestType())
                .status(Request.RequestStatus.APPROVED)
                .content(teacherDTO)
                .build();
    }

    @Override
    public RequestContentResponseDTO rejectRequest(Long requestId) throws JsonProcessingException {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new EntityIdNotFoundException("No se encontro la solicitud con id: " + requestId));
        requestRepository.delete(request);
        RequestContentResponseDTO requestContentResponseDTO  = requestMapper.toDTO(request);
        requestContentResponseDTO.setStatus(Request.RequestStatus.REJECTED);
        return requestContentResponseDTO;
    }


}
