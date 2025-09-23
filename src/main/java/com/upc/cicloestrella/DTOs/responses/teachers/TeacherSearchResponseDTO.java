package com.upc.cicloestrella.DTOs.responses.teachers;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TeacherSearchResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String profilePictureUrl;
    private BigDecimal averageRating;
}
