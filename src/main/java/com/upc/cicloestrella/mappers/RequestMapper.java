package com.upc.cicloestrella.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.upc.cicloestrella.DTOs.requests.TeacherRequestDTO;
import com.upc.cicloestrella.DTOs.responses.CampusResponseDTO;
import com.upc.cicloestrella.DTOs.responses.CareerResponseDTO;
import com.upc.cicloestrella.DTOs.responses.CourseResponseDTO;
import com.upc.cicloestrella.DTOs.responses.RequestContentResponseDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherJsonResponseConversionDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherRequestResponseDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherResponseDTO;
import com.upc.cicloestrella.entities.*;
import com.upc.cicloestrella.enums.RequestTypeEnum;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.repositories.interfaces.application.CampusRepository;
import com.upc.cicloestrella.repositories.interfaces.application.CareerRepository;
import com.upc.cicloestrella.repositories.interfaces.application.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestMapper {

    private final ModelMapper modelMapper;
    private final StudentMapper studentMapper;
    private final ObjectMapper objectMapper;
    private final CampusRepository campusRepository;
    private final CareerRepository careerRepository;
    private final CourseRepository courseRepository;

    public RequestContentResponseDTO toDTO(Request request) throws JsonProcessingException {
        TeacherJsonResponseConversionDTO teacher = objectMapper.treeToValue(request.getContent(),TeacherJsonResponseConversionDTO.class);

        List<CampusResponseDTO> campuses = getCampusesByIds(teacher.getCampusIds())
                .stream()
                .map(campus -> modelMapper.map(campus, CampusResponseDTO.class))
                .toList();
        List<CareerResponseDTO> careers = getCareersByIds(teacher.getCareerIds())
                .stream()
                .map(career -> modelMapper.map(career, CareerResponseDTO.class))
                .toList();
        List<CourseResponseDTO> courses = getCoursesByIds(teacher.getCourseIds())
                .stream()
                .map(course -> modelMapper.map(course, CourseResponseDTO.class))
                .toList();

        TeacherRequestResponseDTO dto = getObjectDTO(teacher);

        dto.setCampuses(campuses);
        dto.setCareers(careers);
        dto.setCourses(courses);

        return RequestContentResponseDTO.builder()
                .id(request.getId())
                .requestType(request.getRequestType())
                .student(studentMapper.toDTO(request.getStudent()))
                .content(dto)
                .status(request.getStatus())
                .createdAt(request.getCreatedAt())
                .build();
    }



    private TeacherRequestResponseDTO getObjectDTO(Object content) {
        if (content instanceof TeacherJsonResponseConversionDTO) {
            return modelMapper.map(content, TeacherRequestResponseDTO.class);
        }
        return null;
    }
    private List<Campus> getCampusesByIds(List<Long> ids) {
        List<Campus> campuses = campusRepository.findAllById(ids);
        if (campuses.size() != ids.size()) {
            throw new EntityIdNotFoundException("Una o más sedes no existen");
        }
        return campuses;
    }

    private List<Career> getCareersByIds(List<Long> ids) {
        List<Career> careers = careerRepository.findAllById(ids);
        if (careers.size() != ids.size()) {
            throw new EntityIdNotFoundException("Una o más carreras no existen");
        }
        return careers;
    }

    private List<Course> getCoursesByIds(List<Long> ids) {
        List<Course> courses = courseRepository.findAllById(ids);
        if (courses.size() != ids.size()) {
            throw new EntityIdNotFoundException("Uno o más cursos no existen");
        }
        return courses;
    }
}