package com.upc.cicloestrella.controllers;

import com.upc.cicloestrella.DTOs.UserRequestDTO;
import com.upc.cicloestrella.DTOs.UserResponseDTO;
import com.upc.cicloestrella.interfaces.services.UserServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserServiceInterface userService;
    @Autowired
    public UserController(UserServiceInterface userService) {
        this.userService = userService;
    }
    @GetMapping("/users")
    public ResponseEntity<?> index() {
        var users = userService.index();
        if(users.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body(
                            Map.of("message", "No se encontraron usuarios", "status", 404)
                    );
        }

        return ResponseEntity
                .status(200)
                .body(
                        Map.of("data", users, "message", "Usuarios obtenidos correctamente", "status", 200)
                );
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        var user = userService.show(id);
        if(user == null) {
            return ResponseEntity
                    .status(404)
                    .body(
                            Map.of("message", "Usuario no encontrado", "status", 404)
                    );
        }

        return ResponseEntity
                .status(200)
                .body(
                        Map.of("data", user, "message", "Usuario obtenido correctamente", "status", 200)
                );
    }

    @PostMapping("/users")
    public ResponseEntity<?> save(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        var createdUser = userService.save(userRequestDTO);
        if(createdUser == null) {
            return ResponseEntity
                    .status(400)
                    .body(
                            Map.of("message", "No se pudo crear el usuario", "status", 400)
                    );
        }

        return ResponseEntity
                .status(201)
                .body(
                        Map.of("data", createdUser, "message", "Usuario creado correctamente", "status", 201)
                );
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        var updatedUser = userService.update(id, userRequestDTO);
        if (updatedUser == null) {
            return ResponseEntity
                    .status(400)
                    .body(
                            Map.of("message", "No se pudo actualizar el usuario", "status", 400)
                    );
        }
        return ResponseEntity
                .status(200)
                .body(
                        Map.of("data", updatedUser, "message", "Usuario actualizado correctamente", "status", 200)
                );
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = userService.show(id);
        if (userResponseDTO == null) {
            return ResponseEntity
                    .status(404)
                    .body(
                            Map.of("message", "Usuario no encontrado", "status", 404)
                    );
        }

        userService.delete(id);

        return ResponseEntity
                .status(200)
                .body(
                        Map.of("message", "Usuario eliminado correctamente", "status", 200)
                );
    }
}
