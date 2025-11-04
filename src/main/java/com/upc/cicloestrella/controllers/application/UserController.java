package com.upc.cicloestrella.controllers.application;

import com.upc.cicloestrella.DTOs.requests.UserRequestDTO;
import com.upc.cicloestrella.DTOs.responses.UserResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.services.application.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR')")
    public ResponseEntity<ApiResponse<UserResponseDTO>> show(@PathVariable Long userId) {
        UserResponseDTO user = userService.findById(userId);
        return ResponseEntity.ok(ApiResponse.<UserResponseDTO>builder()
                .data(user)
                .message("Usuario obtenido correctamente")
                .status(200)
                .build());
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<UserResponseDTO>>> filter(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String roleName,
            @RequestParam(required = false) Boolean state,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate ,
            @RequestParam(required = false) Integer page ,
            @RequestParam(required = false) Integer size
    ) {
        Page<UserResponseDTO> users = userService.index(username, roleName, state, startDate, endDate , page != null ? page : 0 , size != null ? size : 10);
        return ResponseEntity.ok(ApiResponse.<Page<UserResponseDTO>>builder()
                .data(users)
                .message("Usuarios filtrados correctamente")
                .status(200)
                .build());
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponseDTO>> update(@PathVariable Long userId, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO updatedUser = userService.update(userId, userRequestDTO);
        return ResponseEntity.ok(ApiResponse.<UserResponseDTO>builder()
                .data(updatedUser)
                .message("Usuario actualizado correctamente")
                .status(200)
                .build());
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponseDTO>> delete(@PathVariable Long userId) {
        UserResponseDTO deletedUser = userService.delete(userId);
        return ResponseEntity.ok(ApiResponse.<UserResponseDTO>builder()
                .data(deletedUser)
                .message("Usuario eliminado correctamente")
                .status(200)
                .build());
    }

    @PostMapping("/{userId}/restore")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponseDTO>> restore(@PathVariable Long userId) {
        UserResponseDTO restoredUser = userService.revokeBan(userId);
        return ResponseEntity.ok(ApiResponse.<UserResponseDTO>builder()
                .data(restoredUser)
                .message("Usuario restaurado correctamente")
                .status(200)
                .build());
    }
}

