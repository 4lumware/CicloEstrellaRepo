package com.upc.cicloestrella.controllers.application;

import com.upc.cicloestrella.DTOs.requests.reviews.ReviewCreateReactionRequestDTO;
import com.upc.cicloestrella.DTOs.responses.reviews.ReviewReactionResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.application.ReviewReactionServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReviewReactionController
{
    private final ReviewReactionServiceInterface reviewReactionService;

    @PostMapping("/reviews/{reviewId}/reactions/{reactionId}")
    @PreAuthorize("hasAnyRole('STUDENT')")
    public ResponseEntity<ApiResponse<ReviewReactionResponseDTO>> addReactionToReview(@PathVariable Long reviewId, @PathVariable Long reactionId)
    {
        ReviewReactionResponseDTO reviewReactionResponseDTO = reviewReactionService.addReactionToReview(reviewId, reactionId );

        if(reviewReactionResponseDTO == null)
        {
            return ResponseEntity.status(400).body(
                    ApiResponse.<ReviewReactionResponseDTO>builder().status(400).message("No se pudo agregar la reacción a la reseña").build());
        }

        return ResponseEntity.status(201).body(
                ApiResponse.<ReviewReactionResponseDTO>builder().status(201).message("Reacción agregada a la reseña exitosamente").data(reviewReactionResponseDTO).build());

    }

    @DeleteMapping("/reviews/{reviewId}/reactions/{reviewReactionId}")
    @PreAuthorize("hasAnyRole('STUDENT' , 'MODERATOR' , 'ADMIN') and @reviewReactionAuthorizationService.canAccess(authentication , #reviewReactionId)")
    public ResponseEntity<ApiResponse<ReviewReactionResponseDTO>> removeReactionFromReview(@PathVariable Long reviewId, @PathVariable Long reviewReactionId)
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
