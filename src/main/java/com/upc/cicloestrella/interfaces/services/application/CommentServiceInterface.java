package com.upc.cicloestrella.interfaces.services.application;


import com.upc.cicloestrella.DTOs.requests.CommentRequestDTO;
import com.upc.cicloestrella.DTOs.responses.comments.CommentResponseDTO;
import com.upc.cicloestrella.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentServiceInterface {
    Page<CommentResponseDTO> index(String keyword, String studentName, Long formalityId, String formalityTitle, LocalDateTime from, LocalDateTime to , Pageable pageable);
    List<CommentResponseDTO> allByFormalityId(Long formalityId);
    CommentResponseDTO findById(Long commentId , Long formalityId);
    CommentResponseDTO delete(Long commentId , Long formalityId);
    CommentResponseDTO save(CommentRequestDTO comment , Long formalityId);
    CommentResponseDTO update(CommentRequestDTO comment , Long commentId ,  Long formalityId);
}
