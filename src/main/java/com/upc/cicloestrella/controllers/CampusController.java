package com.upc.cicloestrella.controllers;

import com.upc.cicloestrella.DTOs.requests.CampusRequestDTO;
import com.upc.cicloestrella.DTOs.responses.CampusResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.CampusServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/campuses")
public class CampusController {
    private final CampusServiceInterface campusService;

    @Autowired
    public CampusController(CampusServiceInterface campusService) {
        this.campusService = campusService;
    }

    @GetMapping
    public ResponseEntity<?> index() {
        List<CampusResponseDTO> campuses = campusService.index();

        if (campuses.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body(
                            ApiResponse.builder().message("No se encontraron campus").status(404).build()
                    );
        }

        return ResponseEntity
                .status(200)
                .body(
                        ApiResponse.builder().data(campuses).message("Campus obtenidos correctamente").status(200).build()
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        CampusResponseDTO campus = campusService.show(id);

        if (campus == null) {
            return ResponseEntity
                    .status(404)
                    .body(
                            ApiResponse.builder().message("No se encontraron campus").status(404).build()
                    );
        }

        return ResponseEntity
                .status(200)
                .body(
                        ApiResponse.builder().data(campus).message("Campus obtenido correctamente").status(200).build()
                );
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody CampusRequestDTO campusRequestDTO) {
        CampusResponseDTO campus = campusService.save(campusRequestDTO);

        if (campus == null) {
            return ResponseEntity
                    .status(400)
                    .body(
                            Map.of("message", "Error al crear el campus", "status", 400)
                    );
        }

        return ResponseEntity
                .status(201)
                .body(
                        Map.of("data", campus, "message", "Campus creado exitosamente", "status", 201)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CampusRequestDTO campusRequestDTO) {
        CampusResponseDTO campus = campusService.update(id, campusRequestDTO);

        if (campus == null) {
            return ResponseEntity
                    .status(404)
                    .body(
                            Map.of("message", "Campus no encontrado", "status", 404)
                    );
        }

        return ResponseEntity
                .status(200)
                .body(
                        Map.of("data", campus, "message", "Campus actualizado correctamente", "status", 200)
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        CampusResponseDTO campus = campusService.show(id);

        if (campus == null) {
            return ResponseEntity
                    .status(404)
                    .body(
                            Map.of("message", "Campus no encontrado", "status", 404)
                    );
        }

        campusService.delete(id);

        return ResponseEntity
                .status(200)
                .body(
                        Map.of("message", "Campus eliminado correctamente", "status", 200)
                );
    }



}
