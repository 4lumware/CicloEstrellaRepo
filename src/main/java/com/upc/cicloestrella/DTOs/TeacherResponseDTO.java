package com.upc.cicloestrella.DTOs;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TeacherResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String generalDescription;
    private String profilePictureUrl;
    private BigDecimal averageRating;
    private List<CampusResponseDTO> campuses;
    private List<CareerResponseDTO> careers;
    private List<CourseResponseDTO> courses;
}

