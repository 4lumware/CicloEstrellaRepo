package com.upc.cicloestrella.controllers;


import com.upc.cicloestrella.DTOs.TeacherRequestDTO;
import com.upc.cicloestrella.DTOs.TeacherResponseDTO;
import com.upc.cicloestrella.interfaces.services.TeacherServiceInterface;
import com.upc.cicloestrella.services.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TeacherController {
    private final TeacherServiceInterface teacherService;

    @Autowired
    public TeacherController(TeacherServiceInterface teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/teachers")
    public ResponseEntity<?> index(@RequestParam(required = false) String name) {
        List<TeacherResponseDTO> teachers = teacherService.index(name);

        if (teachers.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body(Map.of("message", "No se han encontrado profesores", "status", 404));
        }
        return ResponseEntity
                .status(200)
                .body(Map.of("data", teachers, "message", "Se han encontrado los profesores", "status", 200));
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        TeacherResponseDTO teacher = teacherService.show(id);

        if (teacher == null) {
            return ResponseEntity
                    .status(404)
                    .body(Map.of("message", "No se ha encontrado el profesor", "status", 404));
        }
        return ResponseEntity
                .status(200)
                .body(Map.of("data", teacher, "message", "Se ha encontrado el profesor", "status", 200));
    }

    @PostMapping("/teachers")
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

    @PutMapping("/teachers/{id}")
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

    @DeleteMapping("/teachers/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity
                .status(200)
                .body(Map.of("message", "Se ha eliminado el profesor", "status", 200));
    }
}

