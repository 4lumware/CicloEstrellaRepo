package com.upc.cicloestrella.DTOs.requests;


import com.upc.cicloestrella.entities.Role;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleVerificationRequestDTO {
    @NotEmpty(message = "La lista de roles no debe estar vacía")
    private List<Role.RoleName> roleNames;
}
