package com.upc.cicloestrella.controllers;


import com.upc.cicloestrella.DTOs.FormatRequestDTO;
import com.upc.cicloestrella.DTOs.FormatResponseDTO;
import com.upc.cicloestrella.interfaces.services.FormatServiceInterface;
import com.upc.cicloestrella.services.FormatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FormatController {
    private final FormatServiceInterface formatService;

    @Autowired
    public FormatController(FormatServiceInterface formatService) {
        this.formatService = formatService;
    }

    @GetMapping("/formats")
    public ResponseEntity<?> index() {
        List<FormatResponseDTO> formats = formatService.index();
        if (formats.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body(Map.of("message", "No se encontraron formatos", "status", 404));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("data", formats, "message", "Formatos obtenidos correctamente", "status", 200));
    }

    @PostMapping("/formats")
    public ResponseEntity<?> store(@Valid @RequestBody FormatRequestDTO dto) {
        FormatResponseDTO format = formatService.save(dto);

        if (format == null) {
            return ResponseEntity
                    .status(400)
                    .body(Map.of("message", "Error al crear el formato", "status", 400));
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("data", format, "message", "Formato creado exitosamente", "status", 201));
    }

    @GetMapping("/formats/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        FormatResponseDTO format = formatService.show(id);
        if (format == null) {
            return ResponseEntity
                    .status(404)
                    .body(Map.of("message", "Formato no encontrado", "status", 404));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("data", format, "message", "Formato obtenido correctamente", "status", 200));
    }

    @PutMapping("/formats/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody FormatRequestDTO dto) {
        FormatResponseDTO format = formatService.update(id, dto);
        if (format == null) {
            return ResponseEntity
                    .status(404)
                    .body(Map.of("message", "Formato no encontrado", "status", 404));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("data", format, "message", "Formato actualizado correctamente", "status", 200));
    }

    @DeleteMapping("/formats/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        FormatResponseDTO format = formatService.show(id);
        if (format == null) {
            return ResponseEntity
                    .status(404)
                    .body(Map.of("message", "Formato no encontrado", "status", 404));
        }
        formatService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("message", "Formato eliminado correctamente", "status", 200));
    }

}
