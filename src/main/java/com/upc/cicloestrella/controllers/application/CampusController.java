package com.upc.cicloestrella.controllers.application;

import com.upc.cicloestrella.DTOs.requests.CampusRequestDTO;
import com.upc.cicloestrella.DTOs.responses.CampusResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.application.CampusServiceInterface;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campuses")
public class CampusController {
    private final CampusServiceInterface campusService;

    @Autowired
    public CampusController(CampusServiceInterface campusService) {
        this.campusService = campusService;
    }


    @GetMapping
    @PermitAll
    public ResponseEntity<ApiResponse<List<CampusResponseDTO>>> index() {
        List<CampusResponseDTO> campuses = campusService.index();
        if (campuses.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<List<CampusResponseDTO>>builder()
                            .message("No se encontraron campus")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<List<CampusResponseDTO>>builder()
                        .data(campuses)
                        .message("Campus obtenidos correctamente")
                        .status(200)
                        .build());
    }

    @GetMapping("/{id}")
    @PermitAll
    public ResponseEntity<ApiResponse<CampusResponseDTO>> show(@PathVariable Long id) {
        CampusResponseDTO campus = campusService.show(id);
        if (campus == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<CampusResponseDTO>builder()
                            .message("No se encontraron campus")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<CampusResponseDTO>builder()
                        .data(campus)
                        .message("Campus obtenido correctamente")
                        .status(200)
                        .build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN' , 'MODERATOR')")
    public ResponseEntity<ApiResponse<CampusResponseDTO>> store(@Valid @RequestBody CampusRequestDTO campusRequestDTO) {
        CampusResponseDTO campus = campusService.save(campusRequestDTO);
        if (campus == null) {
            return ResponseEntity.status(400)
                    .body(ApiResponse.<CampusResponseDTO>builder()
                            .message("Error al crear el campus")
                            .status(400)
                            .build());
        }
        return ResponseEntity.status(201)
                .body(ApiResponse.<CampusResponseDTO>builder()
                        .data(campus)
                        .message("Campus creado exitosamente")
                        .status(201)
                        .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN' , 'MODERATOR')")
    public ResponseEntity<ApiResponse<CampusResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody CampusRequestDTO campusRequestDTO) {
        CampusResponseDTO campus = campusService.update(id, campusRequestDTO);
        if (campus == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<CampusResponseDTO>builder()
                            .message("Campus no encontrado")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<CampusResponseDTO>builder()
                        .data(campus)
                        .message("Campus actualizado correctamente")
                        .status(200)
                        .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CampusResponseDTO>> delete(@PathVariable Long id) {
        CampusResponseDTO campus = campusService.show(id);
        if (campus == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<CampusResponseDTO>builder()
                            .message("Campus no encontrado")
                            .status(404)
                            .build());
        }
        campusService.delete(id);
        return ResponseEntity.status(200)
                .body(ApiResponse.<CampusResponseDTO>builder()
                        .data(campus)
                        .message("Campus eliminado correctamente")
                        .status(200)
                        .build());
    }



}
