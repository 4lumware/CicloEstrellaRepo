package com.upc.cicloestrella.services;

import com.upc.cicloestrella.DTOs.CourseRequestDTO;
import com.upc.cicloestrella.DTOs.CourseResponseDTO;
import com.upc.cicloestrella.entities.Course;
import com.upc.cicloestrella.entities.Format;
import com.upc.cicloestrella.interfaces.services.CourseServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.CourseRepository;
import com.upc.cicloestrella.repositories.interfaces.FormatRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService implements CourseServiceInterface {
    private final CourseRepository courseRepository;
    private final FormatRepository formatRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository, FormatRepository formatRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
        this.formatRepository = formatRepository;
    }

    @Override
    public List<CourseResponseDTO> index() {
        return courseRepository.findAll()
                .stream()
                .map(course -> modelMapper.map(course, CourseResponseDTO.class))
                .toList();
    }

    @Override
    public CourseResponseDTO show(Long id) {
        return courseRepository.findById(id)
                .map(course -> modelMapper.map(course, CourseResponseDTO.class))
                .orElse(null);
    }

    @Override
    public CourseResponseDTO save(CourseRequestDTO course) {
        Course courseEntity = new Course();
        courseEntity.setCourseName(course.getCourseName());

        List<Long> formatIds = course.getFormatsIds();
        List<Format> formats = formatRepository.findAllById(formatIds);

        if (formats.size() != formatIds.size()) {
            throw new RuntimeException("Uno o más formatos no existen");
        }

        courseEntity.setFormats(formats);

        Course savedCourse = courseRepository.save(courseEntity);

        return modelMapper.map(savedCourse, CourseResponseDTO.class);
    }
    @Override
    public CourseResponseDTO update(Long id, CourseRequestDTO course) {
        return courseRepository.findById(id)
                .map(existingCourse -> {
                    existingCourse.setCourseName(course.getCourseName());

                    List<Long> formatIds = course.getFormatsIds();
                    List<Format> formats = formatRepository.findAllById(formatIds);

                    if (formats.size() != formatIds.size()) {
                        throw new RuntimeException("Uno o más formatos no existen");
                    }

                    existingCourse.setFormats(formats);

                    Course updatedCourse = courseRepository.save(existingCourse);
                    return modelMapper.map(updatedCourse, CourseResponseDTO.class);
                })
                .orElse(null);
    }

    @Override
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
}
