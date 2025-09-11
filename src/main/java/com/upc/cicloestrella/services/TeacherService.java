package com.upc.cicloestrella.services;

import com.upc.cicloestrella.DTOs.TeacherRequestDTO;
import com.upc.cicloestrella.DTOs.TeacherResponseDTO;
import com.upc.cicloestrella.entities.Campus;
import com.upc.cicloestrella.entities.Career;
import com.upc.cicloestrella.entities.Course;
import com.upc.cicloestrella.entities.Teacher;
import com.upc.cicloestrella.interfaces.services.TeacherServiceInterface;
import com.upc.cicloestrella.repositories.CampusRepository;
import com.upc.cicloestrella.repositories.CareerRepository;
import com.upc.cicloestrella.repositories.CourseRepository;
import com.upc.cicloestrella.repositories.TeacherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TeacherService implements TeacherServiceInterface {

    private final TeacherRepository teacherRepository;
    private final CampusRepository campusRepository;
    private final CareerRepository careerRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public TeacherService(TeacherRepository teacherRepository, CampusRepository campusRepository, CareerRepository careerRepository, CourseRepository courseRepository, ModelMapper modelMapper) {
        this.teacherRepository = teacherRepository;
        this.campusRepository = campusRepository;
        this.careerRepository = careerRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public TeacherResponseDTO save(TeacherRequestDTO teacher) {
        Teacher teacherEntity = new Teacher();
        teacherEntity.setFirstName(teacher.getFirstName());
        teacherEntity.setLastName(teacher.getLastName());
        teacherEntity.setGeneralDescription(teacher.getGeneralDescription());
        teacherEntity.setProfilePictureURL(teacher.getProfilePictureUrl());
        teacherEntity.setAverageRating(BigDecimal.ZERO);  // Inicializar en 0


        List<Campus> campuses = campusRepository.findAllById(teacher.getCampusIds());
        if (campuses.size() != teacher.getCampusIds().size()) {
            throw new RuntimeException("Uno o más campus no existen");
        }

        List<Career> careers = careerRepository.findAllById(teacher.getCareerIds());
        if (careers.size() != teacher.getCareerIds().size()) {
            throw new RuntimeException("Una o más carreras no existen");
        }

        List<Course> courses = courseRepository.findAllById(teacher.getCourseIds());
        if (courses.size() != teacher.getCourseIds().size()) {
            throw new RuntimeException("Uno o más cursos no existen");
        }
        teacherEntity.setCampuses(campuses);
        teacherEntity.setCareers(careers);
        teacherEntity.setCourses(courses);

        Teacher savedTeacher = teacherRepository.save(teacherEntity);

        return modelMapper.map(savedTeacher, TeacherResponseDTO.class);
    }

    @Override
    public List<TeacherResponseDTO> index(String firstName) {

        if (firstName != null && !firstName.isEmpty()) {
            List<Teacher> teachers = teacherRepository.findByFirstNameContainingIgnoreCase(firstName);
            teachers.forEach(teacher -> {
                teacher.getGeneralDescription();
                teacher.getProfilePictureURL();
            });
            return teachers.stream()
                    .map(teacher -> modelMapper.map(teacher, TeacherResponseDTO.class))
                    .toList();
        }
        return teacherRepository.findAll()
                .stream()
                .map(teacher -> modelMapper.map(teacher, TeacherResponseDTO.class))
                .toList();
    }

    @Override
    public TeacherResponseDTO show(Long id) {
        return teacherRepository.findById(id)
                .map(teacher -> modelMapper.map(teacher, TeacherResponseDTO.class))
                .orElse(null);
    }

    @Override
    public TeacherResponseDTO update(Long id, TeacherRequestDTO teacher) {
        return teacherRepository.findById(id)
                .map(existingTeacher -> {
                    existingTeacher.setFirstName(teacher.getFirstName());
                    existingTeacher.setLastName(teacher.getLastName());
                    existingTeacher.setGeneralDescription(teacher.getGeneralDescription());
                    existingTeacher.setProfilePictureURL(teacher.getProfilePictureUrl());

                    List<Campus> campuses = campusRepository.findAllById(teacher.getCampusIds());
                    if (campuses.size() != teacher.getCampusIds().size()) {
                        throw new RuntimeException("Uno o más campus no existen");
                    }

                    List<Career> careers = careerRepository.findAllById(teacher.getCareerIds());
                    if (careers.size() != teacher.getCareerIds().size()) {
                        throw new RuntimeException("Una o más carreras no existen");
                    }

                    List<Course> courses = courseRepository.findAllById(teacher.getCourseIds());
                    if (courses.size() != teacher.getCourseIds().size()) {
                        throw new RuntimeException("Uno o más cursos no existen");
                    }
                    existingTeacher.setCampuses(campuses);
                    existingTeacher.setCareers(careers);
                    existingTeacher.setCourses(courses);

                    Teacher updatedTeacher = teacherRepository.save(existingTeacher);
                    return modelMapper.map(updatedTeacher, TeacherResponseDTO.class);
                })
                .orElse(null);
    }

    @Override
    public void delete(Long id) {
        teacherRepository.deleteById(id);
    }
}
