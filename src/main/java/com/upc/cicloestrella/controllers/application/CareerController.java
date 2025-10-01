package com.upc.cicloestrella.controllers.application;


import com.upc.cicloestrella.DTOs.requests.CareerRequestDTO;
import com.upc.cicloestrella.DTOs.responses.CareerResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.application.CareerServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/careers")
public class CareerController {
    private final CareerServiceInterface careerService;

    @Autowired
    public CareerController(CareerServiceInterface careerService) {
        this.careerService = careerService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CareerResponseDTO>>> index() {
        List<CareerResponseDTO> careers = careerService.index();
        if (careers.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<List<CareerResponseDTO>>builder()
                            .message("No se encontraron carreras")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<List<CareerResponseDTO>>builder()
                        .data(careers)
                        .message("Carreras obtenidas correctamente")
                        .status(200)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CareerResponseDTO>> show(@PathVariable Long id) {
        CareerResponseDTO career = careerService.show(id);
        if (career == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<CareerResponseDTO>builder()
                            .message("Carrera no encontrada")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<CareerResponseDTO>builder()
                        .data(career)
                        .message("Carrera obtenida correctamente")
                        .status(200)
                        .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CareerResponseDTO>> save(@Valid @RequestBody CareerRequestDTO career) {
        CareerResponseDTO savedCareer = careerService.save(career);
        if (savedCareer == null) {
            return ResponseEntity.status(400)
                    .body(ApiResponse.<CareerResponseDTO>builder()
                            .message("Error al crear la carrera")
                            .status(400)
                            .build());
        }
        return ResponseEntity.status(201)
                .body(ApiResponse.<CareerResponseDTO>builder()
                        .data(savedCareer)
                        .message("Carrera creada correctamente")
                        .status(201)
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CareerResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody CareerRequestDTO career) {
        CareerResponseDTO updatedCareer = careerService.update(id, career);
        if (updatedCareer == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<CareerResponseDTO>builder()
                            .message("Carrera no encontrada")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<CareerResponseDTO>builder()
                        .data(updatedCareer)
                        .message("Carrera actualizada correctamente")
                        .status(200)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CareerResponseDTO>> delete(@PathVariable Long id) {
        CareerResponseDTO career = careerService.show(id);
        if (career == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<CareerResponseDTO>builder()
                            .message("Carrera no encontrada")
                            .status(404)
                            .build());
        }
        careerService.delete(id);
        return ResponseEntity.status(200)
                .body(ApiResponse.<CareerResponseDTO>builder()
                        .data(career)
                        .message("Carrera eliminada correctamente")
                        .status(200)
                        .build());
    }
}
