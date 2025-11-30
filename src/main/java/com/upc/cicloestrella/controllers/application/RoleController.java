package com.upc.cicloestrella.controllers.application;

import com.upc.cicloestrella.DTOs.requests.RoleVerificationRequestDTO;
import com.upc.cicloestrella.DTOs.responses.RoleResponseDTO;
import com.upc.cicloestrella.DTOs.responses.RoleVerificationResponseDTO;
import com.upc.cicloestrella.DTOs.responses.StaffResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;

import com.upc.cicloestrella.interfaces.services.application.RoleServiceInterface;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
public class RoleController {
    private final RoleServiceInterface roleService;

    @GetMapping("/roles")
    @PreAuthorize("hasAnyRole('ADMIN' , 'MODERATOR')")
    public ResponseEntity<ApiResponse<List<RoleResponseDTO>>> index(){
        List<RoleResponseDTO> roles = roleService.index();
        if(roles.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<List<RoleResponseDTO>>builder()
                            .message("No se encontraron roles")
                            .status(404)
                            .build());
        }

        return ResponseEntity.status(200)
                .body(ApiResponse.<List<RoleResponseDTO>>builder()
                        .data(roles)
                        .message("Roles obtenidos correctamente")
                        .status(200)
                        .build());
    }

    @GetMapping("/roles/{id}")
    @PreAuthorize("hasAnyRole('ADMIN' , 'MODERATOR')")
    public ResponseEntity<ApiResponse<RoleResponseDTO>> show(@PathVariable Long id) {
        RoleResponseDTO role = roleService.findById(id);
        if(role == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<RoleResponseDTO>builder()
                            .message("No se encontró el rol")
                            .status(404)
                            .build());
        }

        return ResponseEntity.status(200)
                .body(ApiResponse.<RoleResponseDTO>builder()
                        .data(role)
                        .message("Rol obtenido correctamente")
                        .status(200)
                        .build());
    }

    @PostMapping("/users/{userId}/roles/verify")
    @PermitAll
    public ResponseEntity<ApiResponse<RoleVerificationResponseDTO>> verifyUserRole(@PathVariable Long userId, @Valid @RequestBody RoleVerificationRequestDTO roleVerificationRequestDTO) {
        RoleVerificationResponseDTO roleVerificationResponseDTO = roleService.userHasRole(userId, roleVerificationRequestDTO.getRoleNames());

        if(roleVerificationResponseDTO == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<RoleVerificationResponseDTO>builder()
                            .message("No se pudo verificar el rol")
                            .status(404)
                            .build());
        }

        return ResponseEntity.status(200)
                .body(ApiResponse.<RoleVerificationResponseDTO>builder()
                        .data(roleVerificationResponseDTO)
                        .message("Rol verificado correctamente")
                        .status(200)
                        .build());
    }


    @PostMapping("/users/{userId}/roles/{rolId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<StaffResponseDTO>> assignRoleToUser(@PathVariable Long rolId, @PathVariable Long userId) {
        StaffResponseDTO staffResponseDTO = roleService.assignRoleToUser(userId, rolId);
        if(staffResponseDTO == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<StaffResponseDTO>builder()
                            .message("No se pudo asignar el rol")
                            .status(404)
                            .build());
        }

        return ResponseEntity.status(200)
                .body(ApiResponse.<StaffResponseDTO>builder()
                        .data(staffResponseDTO)
                        .message("Rol asignado correctamente")
                        .status(200)
                        .build());
    }

    @DeleteMapping("/users/{userId}/roles/{rolId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<StaffResponseDTO>> removeRoleFromUser(@PathVariable Long rolId, @PathVariable Long userId) {
        StaffResponseDTO staffResponseDTO = roleService.removeRoleFromUser(userId, rolId);
        if(staffResponseDTO == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<StaffResponseDTO>builder()
                            .message("No se pudo eliminar el rol")
                            .status(404)
                            .build());
        }

        return ResponseEntity.status(200)
                .body(ApiResponse.<StaffResponseDTO>builder()
                        .data(staffResponseDTO)
                        .message("Rol eliminado correctamente")
                        .status(200)
                        .build());
    }
}
