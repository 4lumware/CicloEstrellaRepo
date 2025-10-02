package com.upc.cicloestrella.repositories.interfaces.application;

import com.upc.cicloestrella.DTOs.responses.RoleResponseDTO;
import com.upc.cicloestrella.DTOs.responses.StaffResponseDTO;
import com.upc.cicloestrella.entities.Role;
import com.upc.cicloestrella.entities.User;

import java.util.List;

public interface RoleServiceInterface {
    public List<RoleResponseDTO> index();
    public RoleResponseDTO findById(Long id);
    public StaffResponseDTO assignRoleToUser(Long userId, Long roleId);
    public StaffResponseDTO removeRoleFromUser(Long userId, Long roleId);
    public void assignRoleToUser(User user, Role.RoleName roleName);
}
