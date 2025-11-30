package com.upc.cicloestrella.controllers.application;


import com.upc.cicloestrella.DTOs.requests.FormalityRequestDTO;
import com.upc.cicloestrella.DTOs.responses.FormalityResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.application.FormalityServiceInterface;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/formalities")
public class FormalityController {
    private final FormalityServiceInterface formalityService;


    @Autowired
    public FormalityController(FormalityServiceInterface formalityService) {
        this.formalityService = formalityService;
    }

    @GetMapping("/{formalityId}")
    @PermitAll
    public ResponseEntity<ApiResponse<FormalityResponseDTO>> findById(@PathVariable Long formalityId) {
        FormalityResponseDTO tramite = formalityService.findById(formalityId);
        if (tramite == null) {
            return ResponseEntity.status(404).body(ApiResponse.<FormalityResponseDTO>builder().message("Trámite no encontrado").status(404).build());
        }
        return ResponseEntity.status(200).body(ApiResponse.<FormalityResponseDTO>builder().data(tramite).message("Trámite obtenido correctamente").status(200).build());
    }

    @GetMapping
    @PermitAll
    public ResponseEntity<ApiResponse<Page<FormalityResponseDTO>>> findAll(@RequestParam(required = false) String description, @RequestParam(required = false) String title, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to, Pageable pageable) {
        Page<FormalityResponseDTO> formality = formalityService.findAll(title, description, from, to, pageable);
        if (formality.isEmpty()) {
            return ResponseEntity.status(404).body(ApiResponse.<Page<FormalityResponseDTO>>builder().message("No se encontraron trámites").status(404).build());
        }

        return ResponseEntity.status(200).body(ApiResponse.<Page<FormalityResponseDTO>>builder().data(formality).message("Trámites obtenidos correctamente").status(200).build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN' , 'WRITER')")
    public ResponseEntity<ApiResponse<FormalityResponseDTO>> insert(@RequestBody FormalityRequestDTO formalityDTO) {
        FormalityResponseDTO formality = formalityService.insert(formalityDTO);
        if (formality == null) {
            return ResponseEntity.status(400).body(ApiResponse.<FormalityResponseDTO>builder().message("Error al crear el trámite").status(400).build());
        }
        return ResponseEntity.status(201).body(ApiResponse.<FormalityResponseDTO>builder().data(formality).message("Trámite creado correctamente").status(201).build());
    }

    @PutMapping("/{formalityId}")
    @PreAuthorize("hasAnyRole('ADMIN' , 'WRITER')")
    public ResponseEntity<ApiResponse<FormalityResponseDTO>> update(@RequestBody FormalityRequestDTO formality , @PathVariable Long formalityId) {
        FormalityResponseDTO formalityUpdated = formalityService.update(formality , formalityId);

        if (formalityUpdated == null) {
            return ResponseEntity.status(404).body(ApiResponse.<FormalityResponseDTO>builder().message("Trámite no encontrado").status(404).build());
        }
        return ResponseEntity.status(200).body(ApiResponse.<FormalityResponseDTO>builder().data(formalityUpdated).message("Trámite actualizado correctamente").status(200).build());
    }

    @DeleteMapping("/{formalityId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<FormalityResponseDTO>> delete(@PathVariable Long formalityId) {
        FormalityResponseDTO formality = formalityService.findById(formalityId);
        if (formality == null) {
            return ResponseEntity.status(404).body(ApiResponse.<FormalityResponseDTO>builder().message("Trámite no encontrado").status(404).build());
        }
        formalityService.delete(formalityId);
        return ResponseEntity.status(200).body(ApiResponse.<FormalityResponseDTO>builder().data(formality).message("Trámite eliminado correctamente").status(200).build());
    }
}
