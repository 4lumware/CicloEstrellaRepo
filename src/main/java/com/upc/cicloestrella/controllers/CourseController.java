package com.upc.cicloestrella.controllers;


import com.upc.cicloestrella.DTOs.CourseRequestDTO;
import com.upc.cicloestrella.entities.Course;
import com.upc.cicloestrella.interfaces.services.CourseServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CourseController {
    private final CourseServiceInterface courseService;

    @Autowired
    public CourseController(CourseServiceInterface courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/courses")
    public ResponseEntity<?> index() {
        var courses = courseService.index();
        if (courses.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body(
                            Map.of("message", "No se encontraron cursos", "status", 404)
                    );
        }
        return ResponseEntity
                .status(200)
                .body(
                        Map.of("data", courses, "message", "Cursos obtenidos correctamente", "status", 200)
                );
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        var course = courseService.show(id);
        if (course == null) {
            return ResponseEntity
                    .status(404)
                    .body(
                            Map.of("message", "Curso no encontrado", "status", 404)
                    );
        }
        return ResponseEntity
                .status(200)
                .body(
                        Map.of("data", course, "message", "Curso obtenido correctamente", "status", 200)
                );
    }

    @PostMapping("/courses")
    public ResponseEntity<?> store(@Validated @RequestBody CourseRequestDTO course) {
        var createdCourse = courseService.save(course);
        if (createdCourse == null) {
            return ResponseEntity
                    .status(400)
                    .body(
                            Map.of("message", "Error al crear el curso", "status", 400)
                    );
        }
        return ResponseEntity
                .status(201)
                .body(
                        Map.of("data", createdCourse, "message", "Curso creado exitosamente", "status", 201)
                );
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Validated @RequestBody CourseRequestDTO course) {
        var updatedCourse = courseService.update(id, course);
        if (updatedCourse == null) {
            return ResponseEntity
                    .status(404)
                    .body(
                            Map.of("message", "Curso no encontrado", "status", 404)
                    );
        }
        return ResponseEntity
                .status(200)
                .body(
                        Map.of("data", updatedCourse, "message", "Curso actualizado correctamente", "status", 200)
                );
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var course = courseService.show(id);
        if (course == null) {
            return ResponseEntity
                    .status(404)
                    .body(
                            Map.of("message", "Curso no encontrado", "status", 404)
                    );
        }
        courseService.delete(id);
        return ResponseEntity
                .status(200)
                .body(
                        Map.of("message", "Curso eliminado correctamente", "status", 200)
                );
    }
}
