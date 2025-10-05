package com.upc.cicloestrella.DTOs.responses;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String profilePictureUrl;
    private LocalDate creationDate;
    private Boolean state;
    private List<RoleResponseDTO> roles;
}
