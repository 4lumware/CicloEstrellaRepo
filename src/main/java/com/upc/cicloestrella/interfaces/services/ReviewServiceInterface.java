package com.upc.cicloestrella.interfaces.services;

import com.upc.cicloestrella.DTOs.requests.reviews.ReviewRequestDTO;
import com.upc.cicloestrella.DTOs.requests.reviews.ReviewUpdateRequestDTO;
import com.upc.cicloestrella.DTOs.responses.reviews.ReviewResponseDTO;
import java.util.List;

public interface ReviewServiceInterface {
    ReviewResponseDTO save(ReviewRequestDTO reviewRequestDTO);
    List<ReviewResponseDTO> index();
    ReviewResponseDTO show(Long reviewId);
    ReviewResponseDTO update(Long reviewId , ReviewUpdateRequestDTO reviewRequestDTO);
    void delete(Long reviewId );
    List<ReviewResponseDTO> getReviewsByTeacher(Long teacherId , String keyword);
}
