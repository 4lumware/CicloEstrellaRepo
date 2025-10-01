package com.upc.cicloestrella.controllers.auth;


import com.upc.cicloestrella.DTOs.requests.auth.register.UserRegisterRequestDTO;
import com.upc.cicloestrella.DTOs.responses.auth.JsonResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.enums.RoleByAuthenticationMethods;
import com.upc.cicloestrella.repositories.interfaces.auth.AuthServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/staffs")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthAdminController {
    private final AuthServiceInterface authService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<JsonResponseDTO<?>>> register(@RequestBody @Valid UserRegisterRequestDTO userRegisterRequestDTO) {

        JsonResponseDTO<?> jsonResponseDTO = authService.register(userRegisterRequestDTO , RoleByAuthenticationMethods.STAFF);

        return ResponseEntity.status(200)
                .body(
                        ApiResponse.<JsonResponseDTO<?>>builder()
                                .data(jsonResponseDTO)
                                .message("Exito al registrar un nuevo staff")
                                .status(200)
                                .build()
                );
    }

}
