package com.upc.cicloestrella.repositories.interfaces.application;

import com.upc.cicloestrella.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment , Long>  , JpaSpecificationExecutor<Comment> {
    @Query("""
    SELECT c FROM Comment c 
    WHERE c.formality.id = :formalityId
      AND c.student.user.state = true
      AND ( :keyword IS NULL OR LOWER(c.text) LIKE LOWER(CONCAT('%', :keyword, '%')) )
    """)
    List<Comment> findCommentsByFormality_IdAndStudent_User_StateTrue(
            @Param("formalityId") Long formalityId,
            @Param("keyword") String keyword
    );
    Optional<Comment> findByIdAndStudent_User_StateTrueAndFormality_Id(Long id, Long formalityId);


    boolean existsByIdAndStudent_User_EmailAndStudent_User_StateTrue(Long id, String email);

}
