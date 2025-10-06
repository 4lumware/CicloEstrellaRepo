package com.upc.cicloestrella.controllers.application;

import com.upc.cicloestrella.DTOs.requests.CommentRequestDTO;
import com.upc.cicloestrella.DTOs.responses.comments.CommentResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.application.CommentServiceInterface;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceInterface commentService;

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
