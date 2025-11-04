package com.upc.cicloestrella.DTOs.requests.auth.register;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterRequestWithImageDTO {
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 50, message = "El nombre de usuario no debe superar los 50 caracteres")
    private String username;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "Debe ser un correo electrónico válido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotNull(message = "El nombre no puede estar vacío")
    @Size(max = 1000, message = "La URL de la foto no debe superar los 1000 caracteres")

    private String profilePictureUrl;

    @NotNull(message = "El rol no puede estar vacío")
    private Long roleId;

    private int currentSemester;
    private List<@Positive(message = "El ID de la carrera debe ser positivo") Long> careerIds;


}
