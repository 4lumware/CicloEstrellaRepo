package com.upc.cicloestrella.DTOs.database;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactionCountByDatabaseDTO {
    private Long reviewId;
    private Long reactionId;
    private String reactionName;
    private String icon_url;
    private Long count;
}
