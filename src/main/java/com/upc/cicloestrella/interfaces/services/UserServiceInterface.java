package com.upc.cicloestrella.interfaces.services;

import com.upc.cicloestrella.DTOs.UserRequestDTO;
import com.upc.cicloestrella.DTOs.UserResponseDTO;

import java.util.List;

public interface UserServiceInterface {
    public UserResponseDTO save(UserRequestDTO userRequestDTO);
    public List<UserResponseDTO> index();
    public UserResponseDTO show(Long id);
    public UserResponseDTO update(Long id, UserRequestDTO userRequestDTO);
    public void delete(Long id);
}
