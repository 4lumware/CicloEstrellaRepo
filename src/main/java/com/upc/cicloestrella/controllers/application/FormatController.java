package com.upc.cicloestrella.controllers.application;


import com.upc.cicloestrella.DTOs.requests.FormatRequestDTO;
import com.upc.cicloestrella.DTOs.responses.FormatResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.application.FormatServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formats")
public class FormatController {
    private final FormatServiceInterface formatService;

    @Autowired
    public FormatController(FormatServiceInterface formatService) {
        this.formatService = formatService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FormatResponseDTO>>> index() {
        List<FormatResponseDTO> formats = formatService.index();

        if (formats.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<List<FormatResponseDTO>>builder()
                            .message("No se encontraron formatos")
                            .status(404)
                            .build());
        }

        return ResponseEntity.status(200)
                .body(ApiResponse.<List<FormatResponseDTO>>builder()
                        .data(formats)
                        .message("Formatos obtenidos correctamente")
                        .status(200)
                        .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FormatResponseDTO>> store(@Valid @RequestBody FormatRequestDTO dto) {
        FormatResponseDTO format = formatService.save(dto);
        if (format == null) {
            return ResponseEntity.status(400)
                    .body(ApiResponse.<FormatResponseDTO>builder()
                            .message("Error al crear el formato")
                            .status(400)
                            .build());
        }
        return ResponseEntity.status(201)
                .body(ApiResponse.<FormatResponseDTO>builder()
                        .data(format)
                        .message("Formato creado exitosamente")
                        .status(201)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FormatResponseDTO>> show(@PathVariable Long id) {
        FormatResponseDTO format = formatService.show(id);

        if (format == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<FormatResponseDTO>builder()
                            .message("Formato no encontrado")
                            .status(404)
                            .build());
        }

        return ResponseEntity.status(200)
                .body(ApiResponse.<FormatResponseDTO>builder()
                        .data(format)
                        .message("Formato obtenido correctamente")
                        .status(200)
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FormatResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody FormatRequestDTO dto) {
        FormatResponseDTO format = formatService.update(id, dto);
        if (format == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<FormatResponseDTO>builder()
                            .message("Formato no encontrado")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<FormatResponseDTO>builder()
                        .data(format)
                        .message("Formato actualizado correctamente")
                        .status(200)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<FormatResponseDTO>> delete(@PathVariable Long id) {
        FormatResponseDTO format = formatService.show(id);
        if (format == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<FormatResponseDTO>builder()
                            .message("Formato no encontrado")
                            .status(404)
                            .build());
        }
        formatService.delete(id);
        return ResponseEntity.status(200)
                .body(ApiResponse.<FormatResponseDTO>builder()
                        .data(format)
                        .message("Formato eliminado correctamente")
                        .status(200)
                        .build());
    }

}
