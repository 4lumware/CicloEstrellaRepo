package com.upc.cicloestrella.controllers.auth;


import com.upc.cicloestrella.DTOs.requests.auth.login.UserLoginRequestDTO;
import com.upc.cicloestrella.DTOs.responses.auth.JWTTokensDTO;
import com.upc.cicloestrella.DTOs.responses.auth.JsonResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.repositories.interfaces.auth.AuthServiceInterface;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthUserController {
    private final AuthServiceInterface authService;

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<ApiResponse<JsonResponseDTO<?>>> authenticate(@RequestBody @Valid UserLoginRequestDTO userLoginRequestDTO) {
        JsonResponseDTO<?> response = authService.login(userLoginRequestDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<JsonResponseDTO<?>>builder().status(200).message("Se ha autenticado correctamente").data(response).build());

    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<JWTTokensDTO>> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken) {
        JWTTokensDTO response = authService.refreshToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.<JWTTokensDTO>builder().status(200).message("Se ha refrescado el token correctamente").data(response).build());
    }
}
