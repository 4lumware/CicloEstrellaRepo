package com.upc.cicloestrella.controllers;

import com.upc.cicloestrella.DTOs.requests.reviews.ReviewRequestDTO;
import com.upc.cicloestrella.DTOs.requests.reviews.ReviewUpdateRequestDTO;
import com.upc.cicloestrella.DTOs.responses.reviews.ReviewResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.ReviewServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
public class ReviewController {
    private final ReviewServiceInterface reviewService;

    @Autowired
    public ReviewController(ReviewServiceInterface reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public ResponseEntity<ApiResponse<List<ReviewResponseDTO>>> index() {

        List<ReviewResponseDTO> reviews = reviewService.index();

        if (reviews.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<List<ReviewResponseDTO>>builder()
                            .message("No se encontraron reseñas")
                            .status(404)
                            .build());
        }

        return ResponseEntity.status(200)
                .body(ApiResponse.<List<ReviewResponseDTO>>builder()
                        .data(reviews)
                        .message("Reseñas obtenidas correctamente")
                        .status(200)
                        .build());
    }

    @GetMapping("/teachers/{teacherId}/reviews")
    public ResponseEntity<ApiResponse<List<ReviewResponseDTO>>> getReviewsByTeacher(@PathVariable("teacherId") Long teacherId , @RequestParam(required = false) String keyword) {
        List<ReviewResponseDTO> reviews = reviewService.getReviewsByTeacher(teacherId , keyword);

        if (reviews.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<List<ReviewResponseDTO>>builder()
                            .message("No se encontraron reseñas para el profesor con id " + teacherId)
                            .status(404)
                            .build());
        }

        return ResponseEntity.status(200)
                .body(ApiResponse.<List<ReviewResponseDTO>>builder()
                        .data(reviews)
                        .message("Reseñas obtenidas correctamente")
                        .status(200)
                        .build());

    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponseDTO>> show(@PathVariable("reviewId") Long reviewId) {
        ReviewResponseDTO review = reviewService.show(reviewId);
        if (review == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<ReviewResponseDTO>builder()
                            .message("Reseña no encontrada")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<ReviewResponseDTO>builder()
                        .data(review)
                        .message("Reseña obtenida correctamente")
                        .status(200)
                        .build());
    }

    @PostMapping("/reviews")
    public ResponseEntity<ApiResponse<ReviewResponseDTO>> save(@Valid @RequestBody ReviewRequestDTO reviewRequestDTO) {
        ReviewResponseDTO createdReview = reviewService.save(reviewRequestDTO);

        if (createdReview == null) {
            return ResponseEntity.status(400)
                    .body(ApiResponse.<ReviewResponseDTO>builder()
                            .message("Error al crear la reseña")
                            .status(400)
                            .build());
        }
        return ResponseEntity.status(201)
                .body(ApiResponse.<ReviewResponseDTO>builder()
                        .data(createdReview)
                        .message("Reseña creada correctamente")
                        .status(201)
                        .build());
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponseDTO>> update(@PathVariable("reviewId") Long reviewId, @Valid @RequestBody ReviewUpdateRequestDTO reviewRequestDTO) {
        ReviewResponseDTO updatedReview = reviewService.update(reviewId, reviewRequestDTO);
        if (updatedReview == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<ReviewResponseDTO>builder()
                            .message("Reseña no encontrada")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<ReviewResponseDTO>builder()
                        .data(updatedReview)
                        .message("Reseña actualizada correctamente")
                        .status(200)
                        .build());
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponseDTO>> delete(@PathVariable("reviewId") Long reviewId) {
        ReviewResponseDTO review = reviewService.show(reviewId);
        if (review == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<ReviewResponseDTO>builder()
                            .message("Reseña no encontrada")
                            .status(404)
                            .build());
        }
        reviewService.delete(reviewId);
        return ResponseEntity.status(200)
                .body(ApiResponse.<ReviewResponseDTO>builder()
                        .data(review)
                        .message("Reseña eliminada correctamente")
                        .status(200)
                        .build());
    }

}
