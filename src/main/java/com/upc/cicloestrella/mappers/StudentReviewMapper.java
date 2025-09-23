package com.upc.cicloestrella.mappers;

import com.upc.cicloestrella.DTOs.responses.reviews.StudentReviewResponseDTO;
import com.upc.cicloestrella.entities.Student;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentReviewMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public StudentReviewMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.typeMap(Student.class, StudentReviewResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Student::getId, StudentReviewResponseDTO::setId);
                    mapper.map(src -> src.getUser().getUsername(), StudentReviewResponseDTO::setUsername);
                    mapper.map(src -> src.getUser().getProfilePictureUrl(), StudentReviewResponseDTO::setProfilePictureUrl);
                });
    }

    public StudentReviewResponseDTO toDTO(Student student) {
        return modelMapper.map(student, StudentReviewResponseDTO.class);
    }
}
