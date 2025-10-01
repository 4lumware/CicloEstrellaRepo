package com.upc.cicloestrella.controllers.application;

import com.upc.cicloestrella.DTOs.requests.StudentRequestDTO;
import com.upc.cicloestrella.DTOs.responses.StudentResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.application.StudentServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentServiceInterface studentService;
    @Autowired
    public StudentController(StudentServiceInterface studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<StudentResponseDTO>>> index() {
        List<StudentResponseDTO> users = studentService.index();
        if(users.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<List<StudentResponseDTO>>builder()
                            .message("No se encontraron estudiantes")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<List<StudentResponseDTO>>builder()
                        .data(users)
                        .message("Estudiantes obtenidos correctamente")
                        .status(200)
                        .build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> show(@PathVariable Long id) {
        var user = studentService.show(id);
        if(user == null) {
            return ResponseEntity
                    .status(404)
                    .body(
                            new ApiResponse<>( "Estudiante no encontrado", null, 404)
                    );
        }

        return ResponseEntity
                .status(200)
                .body(
                        new ApiResponse<>("Estudiante obtenido correctamente", user, 200)
                );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> delete(@PathVariable Long id) {
        StudentResponseDTO userResponseDTO = studentService.show(id);
        if (userResponseDTO == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<StudentResponseDTO>builder()
                            .message("Estudiante no encontrado")
                            .status(404)
                            .build());
        }
        studentService.delete(id);
        return ResponseEntity.status(200)
                .body(ApiResponse.<StudentResponseDTO>builder()
                        .data(userResponseDTO)
                        .message("Estudiante eliminado correctamente")
                        .status(200)
                        .build());
    }
}
