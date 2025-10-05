package com.upc.cicloestrella.validators.application;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.upc.cicloestrella.DTOs.requests.RequestContentRequestDTO;
import com.upc.cicloestrella.DTOs.requests.TeacherRequestDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherJsonResponseConversionDTO;
import com.upc.cicloestrella.entities.Campus;
import com.upc.cicloestrella.entities.Career;
import com.upc.cicloestrella.entities.Course;
import com.upc.cicloestrella.entities.Teacher;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.exceptions.ValidationWithFieldsException;
import com.upc.cicloestrella.repositories.interfaces.application.CampusRepository;
import com.upc.cicloestrella.repositories.interfaces.application.CareerRepository;
import com.upc.cicloestrella.repositories.interfaces.application.CourseRepository;
import com.upc.cicloestrella.validators.ValidatorInterface;
import jakarta.validation.ConstraintViolation;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TeacherValidationJsonService implements ValidatorInterface<RequestContentRequestDTO,TeacherJsonResponseConversionDTO> {
    private final CampusRepository campusRepository;
    private final CareerRepository careerRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;
    private final ObjectMapper objectMapper;

    @Override
    public TeacherJsonResponseConversionDTO validate(RequestContentRequestDTO obj) throws ValidationWithFieldsException {

        TeacherRequestDTO teacherRequestDTO = objectMapper.convertValue(obj.getContent(), TeacherRequestDTO.class);

        validateDTO(teacherRequestDTO);
        if (existCampusesByIds(teacherRequestDTO.getCampusIds()) &&
                existCareersByIds(teacherRequestDTO.getCareerIds()) &&
                existCoursesByIds(teacherRequestDTO.getCourseIds())) {

            Teacher teacher = modelMapper.map(teacherRequestDTO, Teacher.class);
            return TeacherJsonResponseConversionDTO.builder()
                    .id(teacher.getId())
                    .firstName(teacher.getFirstName())
                    .lastName(teacher.getLastName())
                    .generalDescription(teacher.getGeneralDescription())
                    .profilePictureUrl(teacher.getProfilePictureURL())
                    .averageRating(teacher.getAverageRating())
                    .campusIds(teacherRequestDTO.getCampusIds())
                    .careerIds(teacherRequestDTO.getCareerIds())
                    .courseIds(teacherRequestDTO.getCourseIds())
                    .build();
        }
        return null;

    }

    private <T> void validateDTO(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            Map<String, String> errors = violations.stream()
                    .collect(Collectors.toMap(
                            v -> v.getPropertyPath().toString(),
                            ConstraintViolation::getMessage
                    ));

            throw new ValidationWithFieldsException(errors);
        }
    }

    private boolean existCampusesByIds(List<Long> ids) {
        List<Campus> campuses = (List<Campus>) campusRepository.findAllById(ids);
        if (campuses.size() != ids.size()) {
            throw new EntityIdNotFoundException("Una o más sedes no existen");
        }
        return true;

    }
    private boolean existCareersByIds(List<Long> ids) {
        List<Career> careers = (List<Career>) careerRepository.findAllById(ids);
        if (careers.size() != ids.size()) {
            throw new EntityIdNotFoundException("Una o más carreras no existen");
        }
        return true;
    }

    private boolean existCoursesByIds(List<Long> ids) {
        List<Course> courses = (List<Course>) courseRepository.findAllById(ids);
        if (courses.size() != ids.size()) {
            throw new EntityIdNotFoundException("Uno o más cursos no existen");
        }
        return true;
    }
}
