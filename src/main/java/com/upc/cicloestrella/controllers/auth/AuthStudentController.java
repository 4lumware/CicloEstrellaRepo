package com.upc.cicloestrella.controllers.auth;

import com.upc.cicloestrella.DTOs.requests.auth.register.StudentRegisterRequestDTO;
import com.upc.cicloestrella.DTOs.responses.StudentResponseDTO;
import com.upc.cicloestrella.DTOs.responses.auth.JsonResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.entities.Role;
import com.upc.cicloestrella.enums.RoleByAuthenticationMethods;
import com.upc.cicloestrella.repositories.interfaces.auth.AuthServiceInterface;
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
    private final AuthServiceInterface authService;

    @PostMapping("/register")
    @PermitAll
    public ResponseEntity<ApiResponse<JsonResponseDTO<?>>> register(
            @RequestBody @Valid StudentRegisterRequestDTO userRegisterRequestDTO) throws IOException {

        JsonResponseDTO<?> jsonResponseDTO = authService.register(
                userRegisterRequestDTO , RoleByAuthenticationMethods.STUDENT
        );

        ApiResponse<JsonResponseDTO<?>> response = ApiResponse.<JsonResponseDTO<?>>builder()
                .data(jsonResponseDTO)
                .message("El estudiante se registró correctamente")
                .status(200)
                .build();

        return ResponseEntity.ok(response);
    }
}
