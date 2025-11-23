package com.upc.cicloestrella.services.auth.user.staff;

import com.upc.cicloestrella.DTOs.responses.StaffResponseDTO;
import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.interfaces.services.auth.AuthStaffServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthStaffService  implements AuthStaffServiceInterface {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public StaffResponseDTO me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User staff = userRepository.findByEmailAndStateTrue(username)
                .orElseThrow(() -> new EntityIdNotFoundException("Usuario no encontrado con email " + username));

        return modelMapper.map(staff, StaffResponseDTO.class);
    }
}
