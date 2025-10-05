package com.upc.cicloestrella.controllers.application;

import com.upc.cicloestrella.DTOs.requests.FavoriteRequestDTO;
import com.upc.cicloestrella.DTOs.responses.FavoriteResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.application.FavoriteServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FavoriteController {
    private final FavoriteServiceInterface favoriteService;

    @PreAuthorize("hasAnyRole('STUDENT' , 'ADMIN' , 'MODERATOR') and @favoriteAuthorizationService.canAccess(authentication , #studentId)")
    @GetMapping("/students/{studentId}/favorites")
    public ResponseEntity<ApiResponse<List<FavoriteResponseDTO>>> index(@PathVariable Long studentId) {

        List<FavoriteResponseDTO> favorites = favoriteService.index(studentId);

        if(favorites.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.<List<FavoriteResponseDTO>>builder()
                            .message("No se encontraron favoritos")
                            .status(404)
                            .build());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<List<FavoriteResponseDTO>>builder()
                        .data(favorites)
                        .message("Favoritos obtenidos correctamente")
                        .status(200)
                        .build());
    }

    @PreAuthorize("hasAnyRole('STUDENT' , 'ADMIN' , 'MODERATOR') and @favoriteAuthorizationService.canAccess(authentication , #studentId)")
    @GetMapping("/students/{studentId}/favorites/{favoriteId}")
    public ResponseEntity<ApiResponse<FavoriteResponseDTO>> show(@PathVariable("studentId") Long studentId , @PathVariable("favoriteId") Long favoriteId) {

        FavoriteResponseDTO favorite = favoriteService.findById(studentId, favoriteId);
        if (favorite == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.<FavoriteResponseDTO>builder()
                            .message("No se encontró el favorito")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<FavoriteResponseDTO>builder()
                        .data(favorite)
                        .message("Favorito obtenido correctamente")
                        .status(200)
                        .build());

    }

    @PreAuthorize("hasRole('STUDENT') and @favoriteAuthorizationService.canAccess(authentication , #studentId)")
    @PostMapping("/students/{studentId}/favorites")
    public ResponseEntity<ApiResponse<FavoriteResponseDTO>> save(@PathVariable Long studentId ,  @Valid @RequestBody FavoriteRequestDTO favoriteRequestDTO) {

        FavoriteResponseDTO favorite =  favoriteService.save(studentId, favoriteRequestDTO);
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

    @PreAuthorize("hasAnyRole('STUDENT' , 'ADMIN' , 'MODERATOR') and @favoriteAuthorizationService.canAccess(authentication , #studentId)")
    @DeleteMapping("/students/{studentId}/favorites/{favoriteId}")
    public ResponseEntity<ApiResponse<FavoriteResponseDTO>> delete(@PathVariable ("studentId") Long studentId , @PathVariable("favoriteId") Long favoriteId) {
        FavoriteResponseDTO deleted = favoriteService.delete( studentId , favoriteId);
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
                                .data(deleted)
                                .status(200)
                                .build()
                );
    }
}

