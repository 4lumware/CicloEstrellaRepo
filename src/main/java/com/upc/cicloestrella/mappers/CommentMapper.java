package com.upc.cicloestrella.mappers;


import com.upc.cicloestrella.DTOs.requests.CommentRequestDTO;
import com.upc.cicloestrella.DTOs.responses.comments.CommentResponseDTO;
import com.upc.cicloestrella.DTOs.responses.comments.StudentCommentResponseDTO;
import com.upc.cicloestrella.entities.Comment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
public class CommentMapper {
    private final ModelMapper modelMapper;
    private final StudentCommentMapper studentCommentMapper;

    public CommentResponseDTO toDTO(Comment comment) {
        StudentCommentResponseDTO studentCommentResponseDTO = studentCommentMapper.toDTO(comment.getStudent());
        CommentResponseDTO commentResponseDTO = modelMapper.map(comment, CommentResponseDTO.class);
        commentResponseDTO.setAuthor(studentCommentResponseDTO);
        return commentResponseDTO;
    }

    public Comment toEntity(CommentRequestDTO commentResponseDTO) {
        return modelMapper.map(commentResponseDTO, Comment.class);
    }

    public void ToEntity(CommentRequestDTO commentRequestDTO , Comment comment) {
        modelMapper.map(commentRequestDTO, comment);
    }


}
