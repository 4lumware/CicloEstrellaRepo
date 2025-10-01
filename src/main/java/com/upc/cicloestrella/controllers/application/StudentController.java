package com.upc.cicloestrella.controllers.application;

import com.upc.cicloestrella.DTOs.requests.StudentRequestDTO;
import com.upc.cicloestrella.DTOs.responses.StudentResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.application.StudentServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponseDTO>> save(@Valid @RequestBody StudentRequestDTO userRequestDTO) {
        StudentResponseDTO createdUser = studentService.save(userRequestDTO);
        if (createdUser == null) {
            return ResponseEntity.status(400)
                    .body(ApiResponse.<StudentResponseDTO>builder()
                            .message("Error al crear el estudiante")
                            .status(400)
                            .build());
        }
        return ResponseEntity.status(201)
                .body(ApiResponse.<StudentResponseDTO>builder()
                        .data(createdUser)
                        .message("Estudiante creado correctamente")
                        .status(201)
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody StudentRequestDTO userRequestDTO) {
        StudentResponseDTO updatedUser = studentService.update(id, userRequestDTO);
        if (updatedUser == null) {
            return ResponseEntity.status(400)
                    .body(ApiResponse.<StudentResponseDTO>builder()
                            .message("Error al actualizar el estudiante")
                            .status(400)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<StudentResponseDTO>builder()
                        .data(updatedUser)
                        .message("Estudiante actualizado correctamente")
                        .status(200)
                        .build());
    }

    @DeleteMapping("/{id}")
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
