package com.upc.cicloestrella.controllers;


import com.upc.cicloestrella.DTOs.FormalityDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.entities.Formality;
import com.upc.cicloestrella.interfaces.services.application.FormalityServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formalities")
public class FormalityController {
    private final FormalityServiceInterface formalityService;


    @Autowired
    public FormalityController(FormalityServiceInterface formalityService) {
        this.formalityService = formalityService;
    }

    @GetMapping("/{formalityId}")
    public ResponseEntity<ApiResponse<FormalityDTO>> findById(@PathVariable Long formalityId) {
        FormalityDTO tramite = formalityService.findById(formalityId);
        if (tramite == null) {
            return ResponseEntity.status(404).body(ApiResponse.<FormalityDTO>builder().message("Trámite no encontrado").status(404).build());
        }
        return ResponseEntity.status(200).body(ApiResponse.<FormalityDTO>builder().data(tramite).message("Trámite obtenido correctamente").status(200).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FormalityDTO>>> findAll(@RequestParam(required = false) String keyword) {
        List<FormalityDTO> formality = formalityService.findAll(keyword);
        if (formality.isEmpty()) {
            return ResponseEntity.status(404).body(ApiResponse.<List<FormalityDTO>>builder().message("No se encontraron trámites").status(404).build());
        }
        return ResponseEntity.status(200).body(ApiResponse.<List<FormalityDTO>>builder().data(formality).message("Trámites obtenidos correctamente").status(200).build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FormalityDTO>> insert(@RequestBody FormalityDTO formalityDTO) {
        FormalityDTO formality = formalityService.insert(formalityDTO);
        if (formality == null) {
            return ResponseEntity.status(400)
                    .body(ApiResponse.<FormalityDTO>builder()
                            .message("Error al crear el trámite")
                            .status(400)
                            .build());
        }
        return ResponseEntity.status(201)
                .body(ApiResponse.<FormalityDTO>builder()
                        .data(formality)
                        .message("Trámite creado correctamente")
                        .status(201)
                        .build());
    }

    @PutMapping
    public ResponseEntity<ApiResponse<FormalityDTO>> update(@RequestBody Formality formality) {
        FormalityDTO formalityUpdated = formalityService.update(formality);

        if (formalityUpdated == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<FormalityDTO>builder()
                            .message("Trámite no encontrado")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<FormalityDTO>builder()
                        .data(formalityUpdated)
                        .message("Trámite actualizado correctamente")
                        .status(200)
                        .build());
    }

    @DeleteMapping("/{formalityId}")
    public ResponseEntity<ApiResponse<FormalityDTO>> delete(@PathVariable Long formalityId) {
        FormalityDTO formality = formalityService.findById(formalityId);
        if (formality == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<FormalityDTO>builder()
                            .message("Trámite no encontrado")
                            .status(404)
                            .build());
        }
        formalityService.delete(formalityId);
        return ResponseEntity.status(200)
                .body(ApiResponse.<FormalityDTO>builder()
                        .data(formality)
                        .message("Trámite eliminado correctamente")
                        .status(200)
                        .build());
    }
}
