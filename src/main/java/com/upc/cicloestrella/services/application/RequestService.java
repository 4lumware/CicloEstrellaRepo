package com.upc.cicloestrella.services.application;


import com.upc.cicloestrella.DTOs.requests.RequestContentRequestDTO;
import com.upc.cicloestrella.DTOs.requests.TeacherRequestDTO;
import com.upc.cicloestrella.DTOs.responses.RequestContentResponseDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherResponseDTO;
import com.upc.cicloestrella.entities.Request;
import com.upc.cicloestrella.entities.Student;
import com.upc.cicloestrella.entities.Teacher;
import com.upc.cicloestrella.enums.RequestTypeEnum;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.interfaces.services.application.RequestServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.application.RequestRepository;
import com.upc.cicloestrella.repositories.interfaces.application.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestService implements RequestServiceInterface {
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;
    private final RequestRepository requestRepository;
    private final Validator validator;

    @Override
    public RequestContentResponseDTO save(Long studentId, RequestContentRequestDTO requestContentRequestDTO) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityIdNotFoundException(("No se encontro el estudiante con id: " + studentId)));

        Object contentDTO = validateContentType(requestContentRequestDTO);

        Request request = Request
                .builder()
                .student(student)
                .requestType(requestContentRequestDTO.getRequestType())
                .content(modelMapper.map(contentDTO, Object.class))
                .build();

        Request savedRequest = requestRepository.save(request);

        return RequestContentResponseDTO.builder()
                .id(savedRequest.getId())
                .requestType(savedRequest.getRequestType())
                .content(savedRequest.getContent())
                .status(savedRequest.getStatus())
                .createdAt(savedRequest.getCreatedAt())
                .build();
    }

    @Override
    public RequestContentResponseDTO delete(Long studentId, Long requestId) {
        return null;
    }

    @Override
    public RequestContentResponseDTO show(Long studentId, Long requestId) {
        return null;
    }

    @Override
    public List<RequestContentResponseDTO> findById(Long studentId) {
        return List.of();
    }

    @Override
    public List<RequestContentResponseDTO> index() {
        return List.of();
    }

    private Object validateContentType(RequestContentRequestDTO requestContentRequestDTO) {
        if (Objects.requireNonNull(requestContentRequestDTO.getRequestType()) == RequestTypeEnum.TEACHER) {
            TeacherRequestDTO teacherRequestDTO = modelMapper.map(requestContentRequestDTO.getContent(), TeacherRequestDTO.class);
            validator.validateObject(teacherRequestDTO);
            return modelMapper.map(teacherRequestDTO, TeacherResponseDTO.class);
        }
        return null;
    }
}
