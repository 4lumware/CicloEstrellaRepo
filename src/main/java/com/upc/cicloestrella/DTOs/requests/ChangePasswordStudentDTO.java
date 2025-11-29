package com.upc.cicloestrella.DTOs.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangePasswordStudentDTO {
    @NotNull(message = "La contraseña antigua no puede ser nula")
    private String oldPassword;
    @NotNull(message = "La nueva contraseña no puede ser nula")
    private String newPassword;
}
