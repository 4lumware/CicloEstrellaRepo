package com.upc.cicloestrella.mappers;

import com.upc.cicloestrella.DTOs.responses.comments.StudentCommentResponseDTO;
import com.upc.cicloestrella.DTOs.responses.reviews.StudentReviewResponseDTO;
import com.upc.cicloestrella.entities.Student;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentCommentMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public StudentCommentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.typeMap(Student.class, StudentCommentResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.map(Student::getId, StudentCommentResponseDTO::setId);
                    mapper.map(src -> src.getUser().getUsername(), StudentCommentResponseDTO::setUsername);
                    mapper.map(src -> src.getUser().getProfilePictureUrl(), StudentCommentResponseDTO::setProfilePictureUrl);
                });
    }

    public StudentCommentResponseDTO toDTO(Student student) {
        return modelMapper.map(student, StudentCommentResponseDTO.class);
    }
}
