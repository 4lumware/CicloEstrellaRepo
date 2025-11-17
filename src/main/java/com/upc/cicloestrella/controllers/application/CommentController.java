package com.upc.cicloestrella.controllers.application;

import com.upc.cicloestrella.DTOs.requests.CommentRequestDTO;
import com.upc.cicloestrella.DTOs.responses.comments.CommentResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.entities.Comment;
import com.upc.cicloestrella.interfaces.services.application.CommentServiceInterface;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceInterface commentService;


    @GetMapping("/comments")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public ResponseEntity<ApiResponse<Page<CommentResponseDTO>>> index(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String studentName,
            @RequestParam(required = false) Long formalityId,
            @RequestParam(required = false) String formalityTitle,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            Pageable pageable
    ) {
        Page<CommentResponseDTO> comments = commentService.index(keyword, studentName, formalityId, formalityTitle, from, to, pageable);

        if (comments.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<Page<CommentResponseDTO>>builder()
                            .message("No se encontraron comentarios")
                            .status(404)
                            .build());
        }

        return ResponseEntity.ok(ApiResponse.<Page<CommentResponseDTO>>builder()
                .data(comments)
                .message("Comentarios obtenidos correctamente")
                .status(200)
                .build());
    }
    @GetMapping("/formalities/{formalityId}/comments")
    @PermitAll
    public ResponseEntity<ApiResponse<List<CommentResponseDTO>>> index(
            @PathVariable Long formalityId,
            @RequestParam(required = false) String keyword
    ) {
        List<CommentResponseDTO> comments = commentService.allByFormalityId(formalityId, keyword);
        if (comments.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<List<CommentResponseDTO>>builder()
                            .message("No se encontraron comentarios para el trámite")
                            .status(404)
                            .build());
        }
        return ResponseEntity.ok(ApiResponse.<List<CommentResponseDTO>>builder()
                .data(comments)
                .message("Comentarios obtenidos correctamente")
                .status(200)
                .build());
    }

    @GetMapping("/formalities/{formalityId}/comments/{commentId}")
    @PermitAll
    public ResponseEntity<ApiResponse<CommentResponseDTO>> show(@PathVariable Long commentId, @PathVariable Long formalityId) {
        CommentResponseDTO comment = commentService.findById(commentId , formalityId);

        if(comment == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<CommentResponseDTO>builder()
                            .message("Comentario no encontrado")
                            .status(404)
                            .build());
        }

        return ResponseEntity.ok(ApiResponse.<CommentResponseDTO>builder()
                .data(comment)
                .message("Comentario obtenido correctamente")
                .status(200)
                .build());
    }

    @PostMapping("/formalities/{formalityId}/comments")
    @PreAuthorize("hasAnyRole('STUDENT') and @commentAuthorizationService.canCreate(authentication, #formalityId)")
    public ResponseEntity<ApiResponse<CommentResponseDTO>> create(@PathVariable Long formalityId,
                                                                 @Valid @RequestBody CommentRequestDTO request) {

        CommentResponseDTO created = commentService.save(request , formalityId);
        return ResponseEntity.status(201).body(ApiResponse.<CommentResponseDTO>builder()
                .data(created)
                .message("Comentario creado correctamente")
                .status(201)
                .build());
    }


    @PutMapping("/formalities/{formalityId}/comments/{commentId}")
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN','MODERATOR') and @commentAuthorizationService.canAccess(authentication, #commentId)")
    public ResponseEntity<ApiResponse<CommentResponseDTO>> update(@PathVariable Long formalityId,
                                                                 @PathVariable Long commentId,
                                                                 @Valid @RequestBody CommentRequestDTO request) {

        CommentResponseDTO updated = commentService.update(request, commentId, formalityId);
        return ResponseEntity.ok(ApiResponse.<CommentResponseDTO>builder()
                .data(updated)
                .message("Comentario actualizado correctamente")
                .status(200)
                .build());
    }

    @DeleteMapping("/formalities/{formalityId}/comments/{commentId}")
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN','MODERATOR') and @commentAuthorizationService.canAccess(authentication, #commentId)")
    public ResponseEntity<ApiResponse<CommentResponseDTO>> delete(@PathVariable Long formalityId,
                                                                 @PathVariable Long commentId
                                                                  ) {
        CommentResponseDTO deleted = commentService.delete(commentId, formalityId);
        return ResponseEntity.ok(ApiResponse.<CommentResponseDTO>builder()
                .data(deleted)
                .message("Comentario eliminado correctamente")
                .status(200)
                .build());
    }
}
