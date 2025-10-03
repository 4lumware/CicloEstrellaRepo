package com.upc.cicloestrella.controllers.application;

import com.upc.cicloestrella.DTOs.requests.FavoriteRequestDTO;
import com.upc.cicloestrella.DTOs.responses.FavoriteResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.entities.Favorite;
import com.upc.cicloestrella.interfaces.services.application.FavoriteServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FavoriteController {
    private final FavoriteServiceInterface favoriteService;

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping
    public ResponseEntity<ApiResponse<FavoriteResponseDTO>> save(@Valid @RequestBody FavoriteRequestDTO favoriteRequestDTO) {

        FavoriteResponseDTO favorite = favoriteService.save(favoriteRequestDTO);
        if(favorite == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                            ApiResponse.<FavoriteResponseDTO>builder()
                                    .message("No se ha podido guardar como favorito")
                                    .status(400)
                                    .build()
                    );
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<FavoriteResponseDTO>builder()
                                .message("Se ha guardado como favorito")
                                .data(favorite)
                                .status(201)
                                .build()
                );
    }

    @PreAuthorize("hasRole('STUDENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<FavoriteResponseDTO>> delete(@PathVariable Long id) {
        FavoriteResponseDTO deleted = favoriteService.delete(id);

        if(deleted == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            ApiResponse.<FavoriteResponseDTO>builder()
                                    .message("No se ha encontrado el favorito")
                                    .status(404)
                                    .build()
                    );
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<FavoriteResponseDTO>builder()
                                .message("Se ha eliminado el favorito")
                                .status(200)
                                .build()
                );
    }
}
