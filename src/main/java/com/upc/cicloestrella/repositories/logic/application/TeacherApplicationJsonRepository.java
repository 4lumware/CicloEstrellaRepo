package com.upc.cicloestrella.repositories.logic.application;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherJsonResponseConversionDTO;
import com.upc.cicloestrella.entities.Campus;
import com.upc.cicloestrella.entities.Career;
import com.upc.cicloestrella.entities.Course;
import com.upc.cicloestrella.entities.Teacher;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.repositories.interfaces.application.CampusRepository;
import com.upc.cicloestrella.repositories.interfaces.application.CareerRepository;
import com.upc.cicloestrella.repositories.interfaces.application.CourseRepository;
import com.upc.cicloestrella.repositories.interfaces.application.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TeacherApplicationJsonRepository {
    private final TeacherRepository teacherRepository;
    private final ObjectMapper objectMapper;
    private final CourseRepository courseRepository;
    private final CareerRepository careerRepository;
    private final CampusRepository campusRepository;

    public Teacher save(JsonNode content) throws JsonProcessingException {
        TeacherJsonResponseConversionDTO teacher = objectMapper.treeToValue(content , TeacherJsonResponseConversionDTO.class);
        Teacher newTeacher = Teacher.builder()
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .generalDescription(teacher.getGeneralDescription())
                .profilePictureURL(teacher.getProfilePictureUrl())
                .averageRating(teacher.getAverageRating())
                .courses(getCoursesByIds(teacher.getCourseIds()))
                .careers(getCareersByIds(teacher.getCareerIds()))
                .campuses(getCampusesByIds(teacher.getCampusIds()))
                .build();

        return teacherRepository.save(newTeacher);
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
