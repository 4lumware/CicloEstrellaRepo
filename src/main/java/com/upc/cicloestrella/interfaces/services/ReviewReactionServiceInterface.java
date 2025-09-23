package com.upc.cicloestrella.interfaces.services;

import com.upc.cicloestrella.DTOs.requests.reviews.ReviewCreateReactionRequestDTO;
import com.upc.cicloestrella.DTOs.responses.reviews.ReviewReactionResponseDTO;

public interface ReviewReactionServiceInterface {
    ReviewReactionResponseDTO addReactionToReview(ReviewCreateReactionRequestDTO reviewReactionRequestDTO , Long reviewId);
    ReviewReactionResponseDTO removeReactionFromReview(Long reviewId,  Long reviewReactionId);
}

