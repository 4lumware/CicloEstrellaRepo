package com.upc.cicloestrella.DTOs.responses.reviews;

import com.upc.cicloestrella.DTOs.responses.ReactionCountDTO;
import com.upc.cicloestrella.DTOs.responses.StudentResponseDTO;
import com.upc.cicloestrella.DTOs.responses.TagResponseDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherResponseDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponseDTO {
    private Long id;
    private String description;
    private double rating;
    private StudentReviewResponseDTO student;
    private TeacherResponseDTO teacher;
    private List<TagResponseDTO> tags;
    private List<ReactionCountDTO> reactions;
    private LocalDateTime createdAt;
}
