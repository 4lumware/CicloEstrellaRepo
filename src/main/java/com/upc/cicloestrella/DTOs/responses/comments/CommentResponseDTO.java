package com.upc.cicloestrella.DTOs.responses.comments;

import com.upc.cicloestrella.DTOs.responses.FormalityResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CommentResponseDTO
{
    private Long id;
    private StudentCommentResponseDTO author;
    private FormalityResponseDTO formality;
    private String text;
    private LocalDateTime createdAt;
}
