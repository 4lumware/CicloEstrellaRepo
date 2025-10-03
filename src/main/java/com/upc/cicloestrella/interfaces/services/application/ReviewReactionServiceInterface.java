package com.upc.cicloestrella.interfaces.services.application;

import com.upc.cicloestrella.DTOs.responses.reviews.ReviewReactionResponseDTO;

public interface ReviewReactionServiceInterface {
    ReviewReactionResponseDTO addReactionToReview(Long reviewId , Long reviewReactionId);
    ReviewReactionResponseDTO removeReactionFromReview(Long reviewId,  Long reviewReactionId);
}

