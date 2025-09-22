package com.upc.cicloestrella.controllers;


import com.upc.cicloestrella.DTOs.FormalityDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.entities.Formality;
import com.upc.cicloestrella.interfaces.services.FormalityServiceInterface;
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

    @GetMapping("/{idFormality}")
    public ResponseEntity<ApiResponse<FormalityDTO>> findById(@PathVariable Long idFormality) {
        FormalityDTO formality = formalityService.findById(idFormality);
        if (formality == null) {
            return ResponseEntity.status(404).body(ApiResponse.<FormalityDTO>builder().message("Formalidad no encontrada").status(404).build());
        }
        return ResponseEntity.status(200).body(ApiResponse.<FormalityDTO>builder().data(formality).message("Formalidad obtenida correctamente").status(200).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FormalityDTO>>> findAll() {
        List<FormalityDTO> formalities = formalityService.findAll();
        if (formalities.isEmpty()) {
            return ResponseEntity.status(404).body(ApiResponse.<List<FormalityDTO>>builder().message("No se encontraron formalidades").status(404).build());
        }
        return ResponseEntity.status(200).body(ApiResponse.<List<FormalityDTO>>builder().data(formalities).message("Formalidades obtenidas correctamente").status(200).build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FormalityDTO>> insert(@RequestBody FormalityDTO formalityDTO) {
        FormalityDTO created = formalityService.insert(formalityDTO);
        if (created == null) {
            return ResponseEntity.status(400)
                    .body(ApiResponse.<FormalityDTO>builder()
                            .message("Error al crear la formalidad")
                            .status(400)
                            .build());
        }
        return ResponseEntity.status(201)
                .body(ApiResponse.<FormalityDTO>builder()
                        .data(created)
                        .message("Formalidad creada exitosamente")
                        .status(201)
                        .build());
    }

    @PutMapping
    public ResponseEntity<ApiResponse<FormalityDTO>> update(@RequestBody Formality formality) {
        FormalityDTO updated = formalityService.update(formality);

        if (updated == null) {
            return ResponseEntity.status(400)
                    .body(ApiResponse.<FormalityDTO>builder()
                            .message("Error al actualizar la formalidad")
                            .status(400)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<FormalityDTO>builder()
                        .data(updated)
                        .message("Formalidad actualizada correctamente")
                        .status(200)
                        .build());
    }

    @DeleteMapping("/{idFormality}")
    public ResponseEntity<ApiResponse<FormalityDTO>> delete(@PathVariable Long idFormality) {

        FormalityDTO formality = formalityService.findById(idFormality);

        if (formality == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<FormalityDTO>builder()
                            .message("Formalidad no encontrada")
                            .status(404)
                            .build());
        }
        formalityService.delete(idFormality);
        return ResponseEntity.status(200)
                .body(ApiResponse.<FormalityDTO>builder()
                        .data(formality)
                        .message("Formalidad eliminada correctamente")
                        .status(200)
                        .build());
    }
}
