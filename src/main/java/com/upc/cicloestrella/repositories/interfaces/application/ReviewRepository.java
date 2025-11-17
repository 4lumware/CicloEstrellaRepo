package com.upc.cicloestrella.repositories.interfaces.application;

import com.upc.cicloestrella.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> , JpaSpecificationExecutor<Review> {

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.teacher.id = :teacherId")
    BigDecimal findAverageRatingByTeacherId(@Param("teacherId") Long teacherId);

    Optional<List<Review>> findReviewsByStudent_User_StateTrue();
    Optional<Review> findByIdAndStudent_User_StateTrue(Long id);
    List<Review> findReviewsByTeacherIdAndStudent_User_StateTrue(@Param("teacherId") Long teacherId);

    @Query("SELECT r FROM Review r " +
            "LEFT JOIN r.tags t " +
            "WHERE LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(t.tagName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND r.teacher.id = :teacherId"
            )
    List<Review> findTeacherByDescriptionOrTagNameAndStudent_User_StateTrue(@Param("teacherId") Long teacherId,  @Param("keyword") String keyword);

    boolean existsByIdAndStudent_User_Email(Long id, String studentUserEmail);

}
