package com.upc.cicloestrella.interfaces.services.application;


import com.upc.cicloestrella.DTOs.requests.CommentRequestDTO;
import com.upc.cicloestrella.DTOs.responses.comments.CommentResponseDTO;

import java.util.List;

public interface CommentServiceInterface {
    List<CommentResponseDTO> allByFormalityId(Long formalityId);
    CommentResponseDTO findById(Long commentId , Long formalityId);
    CommentResponseDTO delete(Long commentId , Long formalityId);
    CommentResponseDTO save(CommentRequestDTO comment , Long formalityId);
    CommentResponseDTO update(CommentRequestDTO comment , Long commentId ,  Long formalityId);
}
