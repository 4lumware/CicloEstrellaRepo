package com.upc.cicloestrella.controllers;


import com.upc.cicloestrella.DTOs.requests.TeacherRequestDTO;
import com.upc.cicloestrella.DTOs.responses.TeacherResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.TeacherServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherServiceInterface teacherService;

    @Autowired
    public TeacherController(TeacherServiceInterface teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TeacherResponseDTO>>> index(@RequestParam(required = false) String name) {
        List<TeacherResponseDTO> teachers = teacherService.index(name);

        if (teachers.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<List<TeacherResponseDTO>>builder()
                            .message("No se han encontrado profesores")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<List<TeacherResponseDTO>>builder()
                        .data(teachers)
                        .message("Se han encontrado los profesores")
                        .status(200)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherResponseDTO>> show(@PathVariable Long id) {
        TeacherResponseDTO teacher = teacherService.show(id);

        if (teacher == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<TeacherResponseDTO>builder()
                            .message("Profesor no encontrado")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<TeacherResponseDTO>builder()
                        .data(teacher)
                        .message("Profesor obtenido correctamente")
                        .status(200)
                        .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TeacherResponseDTO>> save(@Valid @RequestBody TeacherRequestDTO teacher) {
        TeacherResponseDTO savedTeacher = teacherService.save(teacher);

        if (savedTeacher == null) {
            return ResponseEntity.status(400)
                    .body(ApiResponse.<TeacherResponseDTO>builder()
                            .message("No se ha podido crear el profesor")
                            .status(400)
                            .build());
        }
        return ResponseEntity.status(201)
                .body(ApiResponse.<TeacherResponseDTO>builder()
                        .data(savedTeacher)
                        .message("Se ha creado el profesor")
                        .status(201)
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody TeacherRequestDTO teacher) {
        TeacherResponseDTO updatedTeacher = teacherService.update(id, teacher);

        if (updatedTeacher == null) {
            return ResponseEntity.status(400)
                    .body(ApiResponse.<TeacherResponseDTO>builder()
                            .message("No se ha podido actualizar el profesor")
                            .status(400)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<TeacherResponseDTO>builder()
                        .data(updatedTeacher)
                        .message("Se ha actualizado el profesor")
                        .status(200)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherResponseDTO>> delete(@PathVariable Long id) {
        TeacherResponseDTO teacher = teacherService.show(id);

        if (teacher == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<TeacherResponseDTO>builder()
                            .message("Profesor no encontrado")
                            .status(404)
                            .build());
        }
        teacherService.delete(id);
        return ResponseEntity.status(200)
                .body(ApiResponse.<TeacherResponseDTO>builder()
                        .data(teacher)
                        .message("Se ha eliminado el profesor")
                        .status(200)
                        .build());
    }
}
