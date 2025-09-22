package com.upc.cicloestrella.controllers;


import com.upc.cicloestrella.DTOs.TeacherRequestDTO;
import com.upc.cicloestrella.DTOs.TeacherResponseDTO;
import com.upc.cicloestrella.DTOs.responses.ApiResponse;
import com.upc.cicloestrella.interfaces.services.TeacherServiceInterface;
import com.upc.cicloestrella.services.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherServiceInterface teacherService;

    @Autowired
    public TeacherController(TeacherServiceInterface teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<?> index(@RequestParam(required = false) String name) {
        List<TeacherResponseDTO> teachers = teacherService.index(name);
        if (teachers.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body(ApiResponse.builder().message("No se han encontrado profesores").status(404).build());
        }
        return ResponseEntity
                .status(200)
                .body(ApiResponse.builder().data(teachers).message("Se han encontrado los profesores").status(200).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        TeacherResponseDTO teacher = teacherService.show(id);
        if (teacher == null) {
            return ResponseEntity
                    .status(404)
                    .body(ApiResponse.builder().message("Profesor no encontrado").status(404).build());
        }
        return ResponseEntity
                .status(200)
                .body(ApiResponse.builder().data(teacher).message("Profesor obtenido correctamente").status(200).build());
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody TeacherRequestDTO teacher) {
        TeacherResponseDTO savedTeacher = teacherService.save(teacher);

        if (savedTeacher == null) {
            return ResponseEntity
                    .status(400)
                    .body(Map.of("message", "No se ha podido crear el profesor", "status", 400));
        }


        return ResponseEntity
                .status(201)
                .body(Map.of("data", savedTeacher, "message", "Se ha creado el profesor", "status", 201));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody TeacherRequestDTO teacher) {
        TeacherResponseDTO updatedTeacher = teacherService.update(id, teacher);
        if (updatedTeacher == null) {
            return ResponseEntity
                    .status(400)
                    .body(Map.of("message", "No se ha podido actualizar el profesor", "status", 400));
        }
        return ResponseEntity
                .status(200)
                .body(Map.of("data", updatedTeacher, "message", "Se ha actualizado el profesor", "status", 200));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity
                .status(200)
                .body(Map.of("message", "Se ha eliminado el profesor", "status", 200));
    }
}
