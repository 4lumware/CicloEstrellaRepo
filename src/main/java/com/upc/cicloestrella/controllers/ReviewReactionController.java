package com.upc.cicloestrella.controllers;

import com.upc.cicloestrella.DTOs.requests.reviews.ReviewCreateReactionRequestDTO;
import com.upc.cicloestrella.DTOs.requests.reviews.ReviewReactionRequestDTO;
import com.upc.cicloestrella.DTOs.responses.reviews.ReviewReactionResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.ReviewReactionServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class ReviewReactionController
{
    private final ReviewReactionServiceInterface reviewReactionService;

    public ReviewReactionController(ReviewReactionServiceInterface reviewReactionService) {
        this.reviewReactionService = reviewReactionService;
    }


    @PostMapping("/reviews/{reviewId}/reactions")
    public ResponseEntity<ApiResponse<ReviewReactionResponseDTO>> addReactionToReview(@RequestBody ReviewCreateReactionRequestDTO reviewReactionRequestDTO, @PathVariable Long reviewId)
    {
        ReviewReactionResponseDTO reviewReactionResponseDTO = reviewReactionService.addReactionToReview(reviewReactionRequestDTO , reviewId);
        if(reviewReactionResponseDTO == null)
        {
            return ResponseEntity.status(400).body(
                    ApiResponse.<ReviewReactionResponseDTO>builder().status(400).message("No se pudo agregar la reacción a la reseña").build());
        }

        return ResponseEntity.status(201).body(
                ApiResponse.<ReviewReactionResponseDTO>builder().status(201).message("Reacción agregada a la reseña exitosamente").data(reviewReactionResponseDTO).build());

    }

    @DeleteMapping("/reviews/{reviewId}/reactions/{reviewReactionId}")
    public ResponseEntity<ApiResponse<ReviewReactionResponseDTO>> removeReactionFromReview(@PathVariable Long reviewId, @PathVariable Long reviewReactionId, @RequestBody ReviewReactionRequestDTO reviewReactionRequestDTO)
    {
        ReviewReactionResponseDTO reviewReactionResponseDTO = reviewReactionService.removeReactionFromReview(reviewId , reviewReactionId);

        if(reviewReactionResponseDTO == null)
        {
            return ResponseEntity.status(400).body(
                    ApiResponse.<ReviewReactionResponseDTO>builder().status(400).message("No se pudo eliminar la reacción de la reseña").build());
        }

        return ResponseEntity.status(200).body(
                ApiResponse.<ReviewReactionResponseDTO>builder().status(200).message("Reacción eliminada de la reseña exitosamente").data(reviewReactionResponseDTO).build());

    }
}
