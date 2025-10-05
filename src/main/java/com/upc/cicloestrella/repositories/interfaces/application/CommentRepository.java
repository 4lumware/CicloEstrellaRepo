package com.upc.cicloestrella.repositories.interfaces.application;

import com.upc.cicloestrella.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment , Long> {
    List<Comment> findCommentsByFormality_IdAndStudent_User_StateTrue(Long formalityId);

    Optional<Comment> findByIdAndStudent_User_StateTrueAndFormality_Id(Long id, Long formalityId);


    boolean existsByStudent_User_EmailAndStudent_User_StateTrueAndId(String studentUserEmail , Long id);

}
