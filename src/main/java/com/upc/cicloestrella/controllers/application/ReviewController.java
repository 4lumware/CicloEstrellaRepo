package com.upc.cicloestrella.controllers.application;

import com.upc.cicloestrella.DTOs.requests.reviews.ReviewRequestDTO;
import com.upc.cicloestrella.DTOs.requests.reviews.ReviewUpdateRequestDTO;
import com.upc.cicloestrella.DTOs.responses.reviews.ReviewResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.entities.Review;
import com.upc.cicloestrella.interfaces.services.application.ReviewServiceInterface;
import com.upc.cicloestrella.services.authorizations.ReviewAuthorizationService;
import com.upc.cicloestrella.specifications.application.ReviewSpecification;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReviewController {
    private final ReviewServiceInterface reviewService;


    @GetMapping("/teachers/{teacherId}/reviews")
    @PermitAll
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
    @PermitAll
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
    @PreAuthorize("hasAnyRole('STUDENT')")
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
    @PreAuthorize("hasAnyRole('STUDENT') and @reviewAuthorizationService.canAccess(authentication, #reviewId)")
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
    @PreAuthorize("hasAnyRole('STUDENT' , 'ADMIN' , 'MODERATOR') and @reviewAuthorizationService.canAccess(authentication, #reviewId)")
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

    @GetMapping("/reviews")
    @PermitAll
    public ResponseEntity<ApiResponse<Page<ReviewResponseDTO>>> index(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long teacherId,
            @RequestParam(required = false) String teacherName,
            @RequestParam(required = false) String studentName,
            @RequestParam(required = false) BigDecimal minRating,
            @RequestParam(required = false) BigDecimal maxRating,
            @RequestParam(required = false) Long tagId,
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            Pageable pageable
        ) {

        Page<ReviewResponseDTO> reviews = reviewService.index(keyword, studentId, teacherId, teacherName, studentName, minRating, maxRating, tagId, tagName, from, to, pageable);

        if (reviews.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<Page<ReviewResponseDTO>>builder()
                            .message("No se encontraron reseñas con los criterios especificados")
                            .status(404)
                            .build());
        }

        return ResponseEntity.ok(
                ApiResponse.<Page<ReviewResponseDTO>>builder()
                        .data(reviews)
                        .message("Reseñas encontradas correctamente")
                        .status(200)
                        .build()
        );
    }

}
