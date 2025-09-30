package com.upc.cicloestrella.repositories.interfaces.application;

import com.upc.cicloestrella.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.teacher.id = :teacherId")
    BigDecimal findAverageRatingByTeacherId(@Param("teacherId") Long teacherId);

    List<Review> findReviewByTeacherId(@Param("teacherId") Long teacherId);

    @Query("SELECT r FROM Review r " +
            "LEFT JOIN r.tags t " +
            "WHERE LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(t.tagName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND r.teacher.id = :teacherId"
            )
    List<Review> findTeacherByDescriptionOrTagName(@Param("teacherId") Long teacherId,  @Param("keyword") String keyword);

}
