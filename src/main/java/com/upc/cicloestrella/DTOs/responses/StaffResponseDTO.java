package com.upc.cicloestrella.DTOs.responses;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StaffResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String profilePictureUrl;
    private LocalDate creationDate;
}
