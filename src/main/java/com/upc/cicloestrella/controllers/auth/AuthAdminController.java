package com.upc.cicloestrella.controllers.auth;


import com.upc.cicloestrella.DTOs.requests.auth.register.StaffRegisterRequestDTO;
import com.upc.cicloestrella.DTOs.requests.auth.register.UserRegisterRequestDTO;
import com.upc.cicloestrella.DTOs.responses.StaffResponseDTO;
import com.upc.cicloestrella.DTOs.responses.auth.JsonResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.enums.RoleByAuthenticationMethods;
import com.upc.cicloestrella.repositories.interfaces.auth.AuthServiceInterface;
import com.upc.cicloestrella.services.auth.register.AuthStaffRegister;
import com.upc.cicloestrella.services.auth.user.staff.AuthStaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth/staffs")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthAdminController {
    private final AuthStaffRegister authStaffRegister;
    private final AuthStaffService authStaffService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<JsonResponseDTO<StaffResponseDTO>>> register(@RequestBody @Valid StaffRegisterRequestDTO dto) throws IOException {

        JsonResponseDTO<StaffResponseDTO> jsonResponseDTO = authStaffRegister.register(dto);

        return ResponseEntity.status(200)
                .body(
                        ApiResponse.<JsonResponseDTO<StaffResponseDTO>>builder()
                                .data(jsonResponseDTO)
                                .message("Exito al registrar un nuevo staff")
                                .status(200)
                                .build()
                );
    }

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR' , 'WRITER')")
    public ResponseEntity<ApiResponse<StaffResponseDTO>> me() {
        StaffResponseDTO staffResponseDTO = authStaffService.me();
        if(staffResponseDTO == null) {
            return ResponseEntity.status(404)
                    .body(
                            ApiResponse.<StaffResponseDTO>builder()
                                    .data(null)
                                    .message("Staff autenticado no encontrado")
                                    .status(404)
                                    .build()
                    );
        }
        return ResponseEntity.status(200)
                .body(
                        ApiResponse.<StaffResponseDTO>builder()
                                .data(staffResponseDTO)
                                .message("Exito al obtener el staff autenticado")
                                .status(200)
                                .build()
                );
    }




}
