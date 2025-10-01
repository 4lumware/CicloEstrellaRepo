package com.upc.cicloestrella.controllers.application;


import com.upc.cicloestrella.DTOs.requests.CourseRequestDTO;
import com.upc.cicloestrella.DTOs.responses.CourseResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.application.CourseServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseServiceInterface courseService;

    @Autowired
    public CourseController(CourseServiceInterface courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponseDTO>>> index() {
        List<CourseResponseDTO> courses = courseService.index();
        if (courses.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<List<CourseResponseDTO>>builder()
                            .message("No se encontraron cursos")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<List<CourseResponseDTO>>builder()
                        .data(courses)
                        .message("Cursos obtenidos correctamente")
                        .status(200)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponseDTO>> show(@PathVariable Long id) {
        CourseResponseDTO course = courseService.show(id);
        if (course == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<CourseResponseDTO>builder()
                            .message("No se encontraron cursos")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<CourseResponseDTO>builder()
                        .data(course)
                        .message("Curso obtenido correctamente")
                        .status(200)
                        .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponseDTO>> store(@Valid @RequestBody CourseRequestDTO course) {
        CourseResponseDTO createdCourse = courseService.save(course);
        if (createdCourse == null) {
            return ResponseEntity.status(400)
                    .body(ApiResponse.<CourseResponseDTO>builder()
                            .message("No se encontraron cursos")
                            .status(400)
                            .build());
        }
        return ResponseEntity.status(201)
                .body(ApiResponse.<CourseResponseDTO>builder()
                        .data(createdCourse)
                        .message("Curso creado correctamente")
                        .status(201)
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody CourseRequestDTO course) {
        CourseResponseDTO updatedCourse = courseService.update(id, course);
        if (updatedCourse == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<CourseResponseDTO>builder()
                            .message("No se encontraron cursos")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<CourseResponseDTO>builder()
                        .data(updatedCourse)
                        .message("Curso actualizado correctamente")
                        .status(200)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponseDTO>> delete(@PathVariable Long id) {
        CourseResponseDTO course = courseService.show(id);
        if (course == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<CourseResponseDTO>builder()
                            .message("No se encontraron cursos")
                            .status(404)
                            .build());
        }
        courseService.delete(id);
        return ResponseEntity.status(200)
                .body(ApiResponse.<CourseResponseDTO>builder()
                        .data(course)
                        .message("Curso eliminado correctamente")
                        .status(200)
                        .build());
    }
}
