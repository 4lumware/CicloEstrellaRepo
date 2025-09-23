package com.upc.cicloestrella.entities.embeddeds;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ReviewReactionID {
    private Long studentId;
    private Long teacherId;
    private Long reactionId;
    private Long authorId;
}
