package com.upc.cicloestrella.services.application;

import com.upc.cicloestrella.DTOs.requests.CommentRequestDTO;
import com.upc.cicloestrella.DTOs.responses.comments.CommentResponseDTO;
import com.upc.cicloestrella.entities.Comment;
import com.upc.cicloestrella.entities.Formality;
import com.upc.cicloestrella.entities.Student;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.interfaces.services.application.CommentServiceInterface;
import com.upc.cicloestrella.mappers.CommentMapper;
import com.upc.cicloestrella.repositories.interfaces.application.CommentRepository;
import com.upc.cicloestrella.repositories.interfaces.application.FormalityRepository;
import com.upc.cicloestrella.services.auth.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommentService implements CommentServiceInterface {
    private final AuthenticatedUserService authenticatedUserService;
    private final CommentRepository commentRepository;
    private final FormalityRepository formalityRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<CommentResponseDTO> allByFormalityId(Long formalityId) {
        return commentRepository.findCommentsByFormality_IdAndStudent_User_StateTrue(formalityId)
                .stream()
                .map(commentMapper::toDTO)
                .toList();
    }

    @Override
    public CommentResponseDTO findById(Long commentId , Long formalityId) {
        return commentRepository.findByIdAndStudent_User_StateTrueAndFormality_Id(commentId, formalityId)
                .map(commentMapper::toDTO)
                .orElseThrow(() ->
                        new EntityIdNotFoundException("No se encontro el comentario con id: " + commentId));
    }

    @Override
    @Transactional
    public CommentResponseDTO delete(Long commentId , Long formalityId) {

        Comment comment = commentRepository.findByIdAndStudent_User_StateTrueAndFormality_Id(commentId,  formalityId)
                .orElseThrow(() -> new EntityIdNotFoundException("No se encontro el comentario con id: " + commentId + " para el tramite con id: " + formalityId));

        commentRepository.delete(comment);

        return commentMapper.toDTO(comment);
    }

    @Override
    @Transactional
    public CommentResponseDTO save(CommentRequestDTO comment , Long formalityId) {
        Student author = authenticatedUserService.getAuthenticatedStudent();
        Formality formality =  formalityRepository.findById(formalityId)
                .orElseThrow(() -> new EntityIdNotFoundException("No se encontro el trámite con id: " + formalityId));

        Comment newComment = commentMapper.toEntity(comment);

        newComment.setStudent(author);
        newComment.setFormality(formality);

        Comment savedComment = commentRepository.save(newComment);

        return commentMapper.toDTO(savedComment);
    }

    @Override
    @Transactional
    public CommentResponseDTO update(CommentRequestDTO comment, Long commentId ,  Long formalityId) {
        Comment existingComment = commentRepository.findByIdAndStudent_User_StateTrueAndFormality_Id(commentId , formalityId)
                .orElseThrow(() -> new EntityIdNotFoundException("No se encontro el comentario con id: " + commentId + " para el tramite de id: " + formalityId));

        Formality formality =  formalityRepository.findById(formalityId)
                .orElseThrow(() -> new EntityIdNotFoundException("No se encontro el trámite con id: " + formalityId));

        commentMapper.ToEntity(comment, existingComment);

        existingComment.setFormality(formality);

        Comment updatedComment = commentRepository.save(existingComment);

        return commentMapper.toDTO(updatedComment);
    }
}
