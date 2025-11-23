package com.upc.cicloestrella.controllers.auth;

import com.upc.cicloestrella.DTOs.requests.auth.register.StudentRegisterRequestDTO;
import com.upc.cicloestrella.DTOs.requests.auth.register.UserRegisterRequestDTO;
import com.upc.cicloestrella.DTOs.responses.StudentResponseDTO;
import com.upc.cicloestrella.DTOs.responses.auth.JsonResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.entities.Role;
import com.upc.cicloestrella.enums.RoleByAuthenticationMethods;
import com.upc.cicloestrella.repositories.interfaces.auth.AuthServiceInterface;
import com.upc.cicloestrella.services.auth.register.AuthStudentRegister;
import com.upc.cicloestrella.services.auth.user.student.AuthStudentService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth/students")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthStudentController{
    private final AuthStudentRegister authStudentRegister;
    private final AuthStudentService authStudentService;

    @PostMapping("/register")
    @PermitAll
    public ResponseEntity<ApiResponse<JsonResponseDTO<StudentResponseDTO>>> register(
            @RequestBody @Valid StudentRegisterRequestDTO userRegisterRequestDTO) throws IOException {

        JsonResponseDTO<StudentResponseDTO> jsonResponseDTO = authStudentRegister.register(userRegisterRequestDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<JsonResponseDTO<StudentResponseDTO>>builder()
                                .data(jsonResponseDTO)
                                .message("Éxito al registrar un nuevo estudiante")
                                .status(200)
                                .build()
                );
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> me() {
        StudentResponseDTO studentResponseDTO = authStudentService.me();
        if(studentResponseDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            ApiResponse.<StudentResponseDTO>builder()
                                    .data(null)
                                    .message("Estudiante autenticado no encontrado")
                                    .status(404)
                                    .build()
                    );
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        ApiResponse.<StudentResponseDTO>builder()
                                .data(studentResponseDTO)
                                .message("Éxito al obtener los datos del estudiante autenticado")
                                .status(200)
                                .build()
                );
    }


}
