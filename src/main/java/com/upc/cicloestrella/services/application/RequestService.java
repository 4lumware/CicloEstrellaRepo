package com.upc.cicloestrella.services.application;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upc.cicloestrella.DTOs.requests.RequestContentRequestDTO;
import com.upc.cicloestrella.DTOs.requests.TeacherRequestDTO;
import com.upc.cicloestrella.DTOs.responses.RequestContentResponseDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherJsonResponseConversionDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherRequestResponseDTO;
import com.upc.cicloestrella.entities.*;
import com.upc.cicloestrella.enums.RequestTypeEnum;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.interfaces.services.application.RequestServiceInterface;
import com.upc.cicloestrella.mappers.RequestMapper;
import com.upc.cicloestrella.repositories.interfaces.application.*;
import com.upc.cicloestrella.services.logic.ImageCreatorService;
import com.upc.cicloestrella.specifications.application.RequestSpecification;
import com.upc.cicloestrella.validators.application.TeacherValidationJsonService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestService implements RequestServiceInterface {
    private final TeacherValidationJsonService teacherValidationJsonService;
    private final StudentRepository studentRepository;
    private final RequestRepository requestRepository;
    private final ObjectMapper objectMapper;
    private final RequestMapper requestMapper;

    @Override
    public RequestContentResponseDTO save(Long studentId, RequestContentRequestDTO requestContentRequestDTO) throws JsonProcessingException {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new EntityIdNotFoundException(("No se encontro el estudiante con id: " + studentId)));

        Object contentDTO = validateContentType(requestContentRequestDTO);
        JsonNode content = objectMapper.convertValue(contentDTO, JsonNode.class);
        Request request = Request
                .builder()
                .student(student)
                .requestType(requestContentRequestDTO.getRequestType())
                .content(content)
                .build();

        Request savedRequest = requestRepository.save(request);
        return requestMapper.toDTO(savedRequest);
    }

    @Override
    public RequestContentResponseDTO delete(Long studentId, Long requestId) throws JsonProcessingException {
        Request request = requestRepository.findByIdAndStudent_User_Id(requestId, studentId)
                .orElseThrow(() -> new EntityIdNotFoundException(("No se encontro la solicitud con id: " + requestId + " para el estudiante con id: " + studentId)));

        requestRepository.delete(request);
        return requestMapper.toDTO(request);

    }

    @Override
    public RequestContentResponseDTO show(Long studentId, Long requestId) throws JsonProcessingException {
        Request request = requestRepository.findByIdAndStudent_User_Id(requestId, studentId)
                .orElseThrow(() -> new EntityIdNotFoundException(("No se encontro la solicitud con id: " + requestId + " para el estudiante con id: " + studentId)));

        return requestMapper.toDTO(request);
    }

    @Override
    public RequestContentResponseDTO findById(Long requestId) throws JsonProcessingException {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new EntityIdNotFoundException(("No se encontro la solicitud con id: " + requestId)));
        return requestMapper.toDTO(request);
    }

    @SneakyThrows
    @Override
    public List<RequestContentResponseDTO> allByStudentId(Long studentId) {
        List<Request> requests = requestRepository.findRequestsByStudent_User_Id(studentId);
        return requests.stream().map(r -> {
            try {
                return requestMapper.toDTO(r);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    @SneakyThrows
    @Override
    public Page<RequestContentResponseDTO> index(Request.RequestStatus status , RequestTypeEnum type , String studentName , LocalDateTime startDate , LocalDateTime endDate , String teacherName , Long courseId , Long campusId ,  int page , int size) {
        Specification<Request> specification = RequestSpecification.build(status , type , studentName , startDate , endDate);
        Pageable pageable = PageRequest.of(page, size);
        List<Request> all = requestRepository.findAll(specification);
        List<RequestContentResponseDTO> filtered = all.stream()
                .map(r -> {
                    try {
                        return requestMapper.toDTO(r);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(dto -> matchesJson(dto, teacherName, courseId, campusId))
                .toList();

        int start = Math.min((int) pageable.getOffset(), filtered.size());
        int end = Math.min((start + pageable.getPageSize()), filtered.size());
        List<RequestContentResponseDTO> pageContent = filtered.subList(start, end);

        return new PageImpl<>(pageContent, pageable, filtered.size());
    }

    private Object validateContentType(RequestContentRequestDTO requestContentRequestDTO) {
        if (Objects.requireNonNull(requestContentRequestDTO.getRequestType()) == RequestTypeEnum.TEACHER) {
            return teacherValidationJsonService.validate(requestContentRequestDTO);

        }
        return null;
    }

    private boolean matchesJson(RequestContentResponseDTO dto, String teacherName, Long courseId, Long campusId) {
        if (dto.getRequestType() == RequestTypeEnum.TEACHER && dto.getContent() instanceof TeacherRequestResponseDTO teacher) {


            if (teacherName != null) {
                String pattern = teacherName.toLowerCase();
                String fullName = (teacher.getFirstName() + " " + teacher.getLastName()).toLowerCase();
                if (!fullName.contains(pattern)) return false;
            }

            if (courseId != null) {
                boolean hasCourse = teacher.getCourses().stream()
                        .anyMatch(c -> c.getId().equals(courseId));
                if (!hasCourse) return false;
            }

            if (campusId != null) {
                boolean hasCampus = teacher.getCampuses().stream()
                        .anyMatch(c -> c.getId().equals(campusId));

                if (!hasCampus) return false;
            }
        }

        return true;
    }
}
