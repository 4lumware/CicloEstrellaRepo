package com.upc.cicloestrella.services.auth;

import com.upc.cicloestrella.entities.Role;
import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.repositories.interfaces.application.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
public class RoleService {

    private final RoleRepository roleRepository;

    public void assignRoleToUser(User user, Role.RoleName roleName) {
        Role role = getRoleByName(roleName);
        user.getRoles().add(role);
    }

    public Role getRoleByName(Role.RoleName roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleName));
    }
}
