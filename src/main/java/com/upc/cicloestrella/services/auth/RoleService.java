package com.upc.cicloestrella.services.auth;

import com.upc.cicloestrella.DTOs.responses.RoleResponseDTO;
import com.upc.cicloestrella.DTOs.responses.RoleVerificationResponseDTO;
import com.upc.cicloestrella.DTOs.responses.StaffResponseDTO;
import com.upc.cicloestrella.entities.Role;
import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.repositories.interfaces.application.RoleRepository;
import com.upc.cicloestrella.interfaces.services.application.RoleServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
public class RoleService implements RoleServiceInterface {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public List<RoleResponseDTO> index(){
        return roleRepository.findAll().stream()
                .map(role -> modelMapper.map(role, RoleResponseDTO.class))
                .toList();
    }

    @Override
    public RoleResponseDTO findById(Long id) {
        return roleRepository.findById(id)
                .map(role -> modelMapper.map(role, RoleResponseDTO.class))
                .orElseThrow(() -> new EntityIdNotFoundException("Rol no encontrado con ID: " + id));
    }

    public StaffResponseDTO assignRoleToUser(Long userId, Long roleId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityIdNotFoundException("Usuario no encontrado con ID: " + userId));

        if(user.getRoles().stream().anyMatch(role -> role.getId().equals(roleId))) {
            throw new EntityIdNotFoundException("El usuario ya tiene asignado este rol.");
        }

        if (user.getRoles().stream().anyMatch(role -> role.getRoleName() == Role.RoleName.STUDENT)) {
            throw new EntityIdNotFoundException("No se pueden asignar roles de administracion a un usuario con rol STUDENT.");
        }

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityIdNotFoundException("Rol no encontrado con ID: " + roleId));

        user.getRoles().add(role);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, StaffResponseDTO.class);

    }

    public StaffResponseDTO removeRoleFromUser(Long userId, Long roleId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityIdNotFoundException("Usuario no encontrado con ID: " + userId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityIdNotFoundException("Rol no encontrado con ID: " + roleId));

        if (!user.getRoles().contains(role)) {
            throw new RuntimeException("El usuario no tiene asignado este rol.");
        }

        if (role.getRoleName() == Role.RoleName.STUDENT) {
            throw new RuntimeException("No se pueden eliminar roles de administracion a un usuario con rol STUDENT.");
        }

        user.getRoles().remove(role);
        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, StaffResponseDTO.class);
    }

    public void assignRoleToUser(User user, Role.RoleName roleName) {
        Role role = getRoleByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public RoleVerificationResponseDTO userHasRole(Long userId, List<Role.RoleName> roleNames) {
        Authentication actualUser = SecurityContextHolder.getContext().getAuthentication();
        String actualUsername = actualUser.getName();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityIdNotFoundException("Usuario no encontrado con ID: " + userId));

        if(!user.getEmail().equals(actualUsername)) {
            throw new RuntimeException("No tienes permiso para verificar los roles de este usuario.");
        }

        boolean hasRole = user.getRoles().stream()
                .anyMatch(role -> roleNames.contains(role.getRoleName()));

        return RoleVerificationResponseDTO.builder()
                .hasRole(hasRole)
                .build();
    }

    private Role getRoleByName(Role.RoleName roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleName));
    }


}
