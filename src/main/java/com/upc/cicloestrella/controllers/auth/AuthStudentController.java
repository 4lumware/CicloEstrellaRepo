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
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth/students")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthStudentController{
    private final AuthStudentRegister authStudentRegister;

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

}
