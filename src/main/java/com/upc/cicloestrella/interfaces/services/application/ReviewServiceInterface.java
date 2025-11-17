package com.upc.cicloestrella.interfaces.services.application;

import com.upc.cicloestrella.DTOs.requests.reviews.ReviewRequestDTO;
import com.upc.cicloestrella.DTOs.requests.reviews.ReviewUpdateRequestDTO;
import com.upc.cicloestrella.DTOs.responses.reviews.ReviewResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ReviewServiceInterface {
    ReviewResponseDTO save(ReviewRequestDTO reviewRequestDTO);
    Page<ReviewResponseDTO> index(
            String keyword,
            Long studentId,
            Long teacherId,
            String teacherName,
            String studentName,
            BigDecimal minRating,
            BigDecimal maxRating,
            Long tagId,
            String tagName,
            LocalDateTime from,
            LocalDateTime to ,
            Pageable pageable
    );
    ReviewResponseDTO show(Long reviewId);
    ReviewResponseDTO update(Long reviewId , ReviewUpdateRequestDTO reviewRequestDTO);
    void delete(Long reviewId );
    List<ReviewResponseDTO> getReviewsByTeacher(Long teacherId , String keyword);
}
