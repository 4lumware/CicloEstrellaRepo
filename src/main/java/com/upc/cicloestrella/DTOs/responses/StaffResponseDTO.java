package com.upc.cicloestrella.DTOs.responses;

import com.upc.cicloestrella.DTOs.responses.teachers.TeacherResponseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StaffResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String profilePictureUrl;
    private LocalDate creationDate;
    private List<RoleResponseDTO> roles;
}
