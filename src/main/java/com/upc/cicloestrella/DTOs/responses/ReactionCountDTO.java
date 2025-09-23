package com.upc.cicloestrella.DTOs.responses;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactionCountDTO {
    private Long id;
    private String reactionName;
    private String icon_url;
    private Long count;
}
