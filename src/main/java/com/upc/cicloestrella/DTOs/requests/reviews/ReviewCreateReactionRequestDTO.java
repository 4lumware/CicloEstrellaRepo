package com.upc.cicloestrella.DTOs.requests.reviews;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateReactionRequestDTO {
    private Long reactionId;
    private Long authorId;
}
