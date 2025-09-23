package com.upc.cicloestrella.mappers;

import com.upc.cicloestrella.DTOs.responses.StudentResponseDTO;
import com.upc.cicloestrella.entities.Student;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public StudentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        this.modelMapper.typeMap(Student.class , StudentResponseDTO.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getUser().getId(), StudentResponseDTO::setId);
                    mapper.map(src -> src.getUser().getUsername(), StudentResponseDTO::setUsername);
                    mapper.map(src -> src.getUser().getEmail(), StudentResponseDTO::setEmail);
                    mapper.map(src -> src.getUser().getProfilePictureUrl(), StudentResponseDTO::setProfilePictureUrl);
                    mapper.map(src -> src.getUser().getCreationDate(), StudentResponseDTO::setCreationDate);
                    mapper.map(Student::getCurrentSemester, StudentResponseDTO::setCurrentSemester);
                    mapper.map(Student::getCareers, StudentResponseDTO::setCareers);
                });

    }

    public StudentResponseDTO toDTO(Student student) {
        return modelMapper.map(student, StudentResponseDTO.class);
    }

}
