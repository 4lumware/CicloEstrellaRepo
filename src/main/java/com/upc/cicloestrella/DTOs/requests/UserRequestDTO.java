package com.upc.cicloestrella.DTOs.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 50, message = "El nombre de usuario no debe superar los 50 caracteres")
    private String username;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "Debe ser un correo electrónico válido")
    private String email;

    @NotNull(message = "El nombre no puede estar vacío")
    @Size(max = 500, message = "La URL de la foto no debe superar los 500 caracteres")
    @Pattern(
            regexp = "^(https?|ftp)://.*$",
            message = "La URL de la foto debe ser válida"
    )
    private String profilePictureUrl;
}
