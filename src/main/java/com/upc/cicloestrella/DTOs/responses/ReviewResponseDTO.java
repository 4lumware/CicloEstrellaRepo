package com.upc.cicloestrella.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ReviewResponseDTO {

    private Long id;
    private String comment;
    private int rating;
    private StudentResponseDTO student;
    private TeacherResponseDTO teacher;
    private LocalDateTime createdAt;

}
