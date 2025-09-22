package com.upc.cicloestrella.controllers;

import com.upc.cicloestrella.DTOs.StudentRequestDTO;
import com.upc.cicloestrella.DTOs.StudentResponseDTO;
import com.upc.cicloestrella.DTOs.responses.ApiResponse;
import com.upc.cicloestrella.interfaces.services.StudentServiceInterface;
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
    public ResponseEntity<?> index() {
        var users = studentService.index();
        if(users.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body(
                            ApiResponse.builder().message("No se encontraron estudiantes").status(404).build()
                    );
        }
        return ResponseEntity
                .status(200)
                .body(
                        ApiResponse.builder().data(users).message("Estudiantes obtenidos correctamente").status(200).build()
                );
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
        var createdUser = studentService.save(userRequestDTO);

        if(createdUser == null) {
            return ResponseEntity
                    .status(400)
                    .body(
                            new ApiResponse<>( "Error al crear el estudiante", null, 400)
                    );
        }

        return ResponseEntity
                .status(201)
                .body(
                        new ApiResponse<>("Estudiante creado correctamente", createdUser, 201)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody StudentRequestDTO userRequestDTO) {
        var updatedUser = studentService.update(id, userRequestDTO);
        if (updatedUser == null) {
            return ResponseEntity
                    .status(400)
                    .body(
                            new ApiResponse<>( "Error al actualizar el estudiante", null, 400)
                    );
        }
        return ResponseEntity
                .status(200)
                .body(
                        new ApiResponse<>("Estudiante actualizado correctamente", updatedUser, 200)
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> delete(@PathVariable Long id) {
        StudentResponseDTO userResponseDTO = studentService.show(id);
        if (userResponseDTO == null) {
            return ResponseEntity
                    .status(404)
                    .body(
                            new ApiResponse<>( "Estudiante no encontrado", null, 404)
                    );
        }

        studentService.delete(id);

        return ResponseEntity
                .status(200)
                .body(
                        new ApiResponse<>("Estudiante eliminado correctamente", userResponseDTO, 200)
                );
    }
}
