package com.upc.cicloestrella.interfaces.services.application;

import com.upc.cicloestrella.DTOs.requests.UserRequestDTO;
import com.upc.cicloestrella.DTOs.responses.UserResponseDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface UserServiceInterface {
    UserResponseDTO findById(Long userId);
    Page<UserResponseDTO> index(String username, String roleName, Boolean state, LocalDate startDate, LocalDate endDate , int page , int size);
    UserResponseDTO delete(Long userId);
    UserResponseDTO update(Long userId, UserRequestDTO user);
}
