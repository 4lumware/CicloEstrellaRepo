package com.upc.cicloestrella.services.application;

import com.upc.cicloestrella.DTOs.requests.TeacherRequestDTO;
import com.upc.cicloestrella.DTOs.responses.TagResponseDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherFindByIdResponseDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherResponseDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherSearchByKeywordResponseDTO;
import com.upc.cicloestrella.entities.*;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.interfaces.services.application.TeacherServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.application.*;
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
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    public TeacherService(TeacherRepository teacherRepository, CampusRepository campusRepository, CareerRepository careerRepository, CourseRepository courseRepository, TagRepository tagRepository, ModelMapper modelMapper) {
        this.teacherRepository = teacherRepository;
        this.campusRepository = campusRepository;
        this.careerRepository = careerRepository;
        this.courseRepository = courseRepository;
        this.tagRepository = tagRepository;
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
            throw new EntityIdNotFoundException("Uno o más campus no existen");
        }

        List<Career> careers = careerRepository.findAllById(teacher.getCareerIds());
        if (careers.size() != teacher.getCareerIds().size()) {
            throw new EntityIdNotFoundException("Una o más carreras no existen");
        }

        List<Course> courses = courseRepository.findAllById(teacher.getCourseIds());
        if (courses.size() != teacher.getCourseIds().size()) {
            throw new EntityIdNotFoundException("Uno o más cursos no existen");
        }
        teacherEntity.setCampuses(campuses);
        teacherEntity.setCareers(careers);
        teacherEntity.setCourses(courses);

        Teacher savedTeacher = teacherRepository.save(teacherEntity);

        return modelMapper.map(savedTeacher, TeacherResponseDTO.class);
    }

    @Override
    public List<TeacherSearchByKeywordResponseDTO> index(String firstName) {

        if (firstName != null && !firstName.isEmpty()) {
            List<Teacher> teachers = teacherRepository.findByFirstNameContainingIgnoreCase(firstName);
            teachers.forEach(teacher -> {
                teacher.getGeneralDescription();
                teacher.getProfilePictureURL();
            });

            return teachers.stream()
                    .map(teacher -> modelMapper.map(teacher, TeacherSearchByKeywordResponseDTO.class))
                    .toList();
        }
        return teacherRepository.findAll()
                .stream()
                .map(teacher -> modelMapper.map(teacher, TeacherSearchByKeywordResponseDTO.class))
                .toList();
    }

    @Override
    public TeacherFindByIdResponseDTO show(Long id) {
        List<Tag> tags = tagRepository.findTop4TagsByTeacher(id);

        List<TagResponseDTO> tagDTOs = tags.stream()
                .map(tag -> modelMapper.map(tag, TagResponseDTO.class))
                .toList();

        Teacher teachers = teacherRepository.findById(id).orElseThrow(() -> new EntityIdNotFoundException("Profesor con id " + id + " no encontrado"));

        TeacherFindByIdResponseDTO teacherDTO = modelMapper.map(teachers, TeacherFindByIdResponseDTO.class);

        teacherDTO.setTags(tagDTOs);

        return teacherDTO;
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

    @Override
    public List<Teacher> searchByCampuses(String campuses) {
        return teacherRepository.searchByCampuses(campuses);
    }

    @Override
    public List<Teacher> searchByCourses(String courses) {
        return teacherRepository.searchByCourses(courses);
    }

    @Override
    public List<Teacher> searchByCareers(String careers) {
        return teacherRepository.searchByCareers(careers);
    }

}
