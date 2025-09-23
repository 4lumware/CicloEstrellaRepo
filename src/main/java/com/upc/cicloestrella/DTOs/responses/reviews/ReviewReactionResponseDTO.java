package com.upc.cicloestrella.DTOs.responses.reviews;

import com.upc.cicloestrella.DTOs.responses.ReactionResponseDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewReactionResponseDTO {
    private Long id;
    private StudentReviewResponseDTO author;
    private ReactionResponseDTO reaction;
    private LocalDateTime createdAt;
}
