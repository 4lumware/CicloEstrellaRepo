package com.upc.cicloestrella.controllers;

import com.upc.cicloestrella.DTOs.requests.ReactionRequestDTO;
import com.upc.cicloestrella.DTOs.responses.ReactionResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.application.ReactionServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reactions")
public class ReactionController {
    private final ReactionServiceInterface reactionService;

    @Autowired
    public ReactionController(ReactionServiceInterface reactionService) {
        this.reactionService = reactionService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReactionResponseDTO>>> index(@RequestParam(required = false) String name ) {
        List<ReactionResponseDTO> reactions = reactionService.index(name);

        if (reactions.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<List<ReactionResponseDTO>>builder()
                            .message("No se encontraron reacciones")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<List<ReactionResponseDTO>>builder()
                        .data(reactions)
                        .message("Reacciones obtenidas correctamente")
                        .status(200)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReactionResponseDTO>> show(@PathVariable Long id) {
        ReactionResponseDTO reaction = reactionService.show(id);
        if (reaction == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<ReactionResponseDTO>builder()
                            .message("Reacción no encontrada")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<ReactionResponseDTO>builder()
                        .data(reaction)
                        .message("Reacción obtenida correctamente")
                        .status(200)
                        .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReactionResponseDTO>> save(@Valid @RequestBody ReactionRequestDTO reactionRequestDTO) {
        ReactionResponseDTO createdReaction = reactionService.save(reactionRequestDTO);
        if (createdReaction == null) {
            return ResponseEntity.status(400)
                    .body(ApiResponse.<ReactionResponseDTO>builder()
                            .message("Error al crear la reacción")
                            .status(400)
                            .build());
        }
        return ResponseEntity.status(201)
                .body(ApiResponse.<ReactionResponseDTO>builder()
                        .data(createdReaction)
                        .message("Reacción creada correctamente")
                        .status(201)
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReactionResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody ReactionRequestDTO reactionRequestDTO) {
        ReactionResponseDTO updatedReaction = reactionService.update(id, reactionRequestDTO);
        if (updatedReaction == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<ReactionResponseDTO>builder()
                            .message("Reacción no encontrada")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<ReactionResponseDTO>builder()
                        .data(updatedReaction)
                        .message("Reacción actualizada correctamente")
                        .status(200)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ReactionResponseDTO>> delete(@PathVariable Long id) {
        ReactionResponseDTO reaction = reactionService.show(id);
        if (reaction == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<ReactionResponseDTO>builder()
                            .message("Reacción no encontrada")
                            .status(404)
                            .build());
        }
        reactionService.delete(id);
        return ResponseEntity.status(200)
                .body(ApiResponse.<ReactionResponseDTO>builder()
                        .data(reaction)
                        .message("Reacción eliminada correctamente")
                        .status(200)
                        .build());
    }
}
