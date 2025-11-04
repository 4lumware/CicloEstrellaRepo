package com.upc.cicloestrella.DTOs.requests.auth.register;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StaffRegisterRequestDTO extends UserRegisterRequestDTO {
    @NotNull(message = "El rol no pueden estar vacío")
    private Long roleId;
}

