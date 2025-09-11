package com.upc.cicloestrella.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private Boolean state;
    private LocalDate creationDate;
    private String profilePictureUrl;
}
