package com.upc.cicloestrella.DTOs.responses.comments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentCommentResponseDTO {
    private Long id;
    private String username;
    private String profilePictureUrl;
}
