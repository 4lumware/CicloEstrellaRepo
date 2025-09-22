package com.upc.cicloestrella.controllers;


import com.upc.cicloestrella.DTOs.CareerRequestDTO;
import com.upc.cicloestrella.DTOs.responses.ApiResponse;
import com.upc.cicloestrella.interfaces.services.CareerServiceInterface;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/careers")
public class CareerController {
    private final CareerServiceInterface careerService;

    @Autowired
    public CareerController(CareerServiceInterface careerService) {
        this.careerService = careerService;
    }

    @GetMapping
    public ResponseEntity<?> index() {
        var careers = careerService.index();

        if (careers.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body(
                            ApiResponse.builder().message("No se encontraron carreras").status(404).build()
                    );
        }

        return ResponseEntity
                .status(200)
                .body(
                        ApiResponse.builder().data(careers).message("Carreras obtenidas correctamente").status(200).build()
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        var career = careerService.show(id);

        if (career == null) {

            return ResponseEntity
                    .status(404)
                    .body(
                            ApiResponse.builder().message("Carrera no encontrada").status(404).build()
                    );
        }

        return ResponseEntity
                .status(200)
                .body(
                        ApiResponse.builder().data(career).message("Carrera obtenida correctamente").status(200).build()
                );
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody CareerRequestDTO career) {
        var savedCareer = careerService.save(career);

        if (savedCareer == null) {
            return ResponseEntity
                    .status(400)
                    .body(
                            ApiResponse.builder().message("Error al crear la carrera").status(400).build()
                    );
        }

        return ResponseEntity
                .status(201)
                .body(
                        ApiResponse.builder().data(savedCareer).message("Carrera creada correctamente").status(201).build()
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody CareerRequestDTO career) {
        var updatedCareer = careerService.update(id, career);
        if (updatedCareer == null) {
            return ResponseEntity
                    .status(404)
                    .body(
                            ApiResponse.builder().message("Carrera no encontrada").status(404).build()
                    );
        }

        return ResponseEntity
                .status(200)
                .body(
                        ApiResponse.builder().data(updatedCareer).message("Carrera actualizada correctamente").status(200).build()
                );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var career = careerService.show(id);
        if (career == null) {
            return ResponseEntity
                    .status(404)
                    .body(
                            ApiResponse.builder().message("Carrera no encontrada").status(404).build()
                    );
        }

        careerService.delete(id);

        return ResponseEntity
                .status(200)
                .body(
                        ApiResponse.builder().message("Carrera eliminada correctamente").status(200).build()
                );
    }
}
