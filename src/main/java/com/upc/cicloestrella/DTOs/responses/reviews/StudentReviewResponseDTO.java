package com.upc.cicloestrella.DTOs.responses.reviews;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentReviewResponseDTO {
    private Long id;
    private String username;
    private String profilePictureUrl;
}
