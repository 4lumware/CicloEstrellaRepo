package com.upc.cicloestrella.repositories.interfaces.application;

import com.upc.cicloestrella.DTOs.database.ReactionCountByDatabaseDTO;
import com.upc.cicloestrella.entities.Review;
import com.upc.cicloestrella.entities.ReviewReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewReactionRepository extends JpaRepository<ReviewReaction, Long> {

    @Query("SELECT new com.upc.cicloestrella.DTOs.database.ReactionCountByDatabaseDTO(" +
            "r.review.id , r.reaction.id , r.reaction.reactionName , r.reaction.icon_url, COUNT(r)) " +
            "FROM ReviewReaction r " +
            "WHERE r.review IN :reviews " +
            "AND r.author.user.state IS true " +
            "GROUP BY r.review.id , r.reaction.id , r.reaction.reactionName , r.reaction.icon_url")
    List<ReactionCountByDatabaseDTO> countAllReactionsByReview(@Param("reviews") List<Review> reviews);

    boolean existsReviewReactionByReview_IdAndAuthor_IdAndReaction_IdAndAuthor_User_StateTrue(Long reviewId, Long authorId, Long reactionId);
    boolean existsReviewReactionByReview_IdAndAuthor_User_StateTrue(Long reviewId);

    boolean existsReviewReactionByIdAndAuthor_User_Email(Long id, String authorUserEmail);
}
