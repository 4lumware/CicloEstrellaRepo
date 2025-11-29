package com.upc.cicloestrella.repositories.interfaces.application;

import com.upc.cicloestrella.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.teacher.id = :teacherId")
    BigDecimal findAverageRatingByTeacherId(@Param("teacherId") Long teacherId);

    Optional<Review> findByIdAndStudent_User_StateTrue(Long id);

    List<Review> findReviewsByTeacherIdAndStudent_User_StateTrue(@Param("teacherId") Long teacherId);

    @Query("SELECT r FROM Review r " + "LEFT JOIN r.tags t " + "WHERE LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " + "   OR LOWER(t.tagName) LIKE LOWER(CONCAT('%', :keyword, '%')) " + "AND r.teacher.id = :teacherId")
    List<Review> findTeacherByDescriptionOrTagNameAndStudent_User_StateTrue(@Param("teacherId") Long teacherId, @Param("keyword") String keyword);

    boolean existsByIdAndStudent_User_Email(Long id, String studentUserEmail);

    @Query("""
            SELECT t.id, t.firstName, t.lastName, AVG(r.rating)
            FROM Review r
            JOIN r.teacher t
            GROUP BY t.id, t.firstName, t.lastName
            ORDER BY AVG(r.rating) DESC
            LIMIT  :limit
            """)
    List<Object[]> findAvgRatingPerTeacher(@Param("limit") int limit);


    @Query("""
            SELECT AVG(r.rating)
            FROM Review r
            WHERE r.createdAt >= :start
              AND r.createdAt < :end
            """)
    BigDecimal findAvgRatingBetween(LocalDateTime start, LocalDateTime end);

    long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query(""" 
        SELECT r FROM Review r
        ORDER BY r.rating DESC
        LIMIT :limit
                """)
    List<Review> findTopReviews(@Param("limit") int limit);
}
