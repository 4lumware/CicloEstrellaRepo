package com.upc.cicloestrella.services.application;


import com.upc.cicloestrella.DTOs.requests.UserRequestDTO;
import com.upc.cicloestrella.DTOs.responses.UserResponseDTO;
import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.interfaces.services.application.UserServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import com.upc.cicloestrella.specifications.application.UserSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Override
    public UserResponseDTO findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityIdNotFoundException("No se encontro el usuario con id: " + userId));
        return modelMapper.map(user, UserResponseDTO.class);
    }



    @Override
    public UserResponseDTO delete(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityIdNotFoundException("No se encontro el usuario con id: " + userId));
        user.setState(false);
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO update(Long userId, UserRequestDTO user) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new EntityIdNotFoundException("No se encontro el usuario con id: " + userId));
        modelMapper.map(user, existingUser);
        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserResponseDTO.class);
    }


    @Override
    public List<UserResponseDTO> index(String username, String roleName, Boolean state, LocalDate startDate, LocalDate endDate) {
        Specification<User> spec = UserSpecification.build(username, roleName, state, startDate, endDate);
        List<User> users = userRepository.findAll(spec);
        return users.stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());
    }

    public UserResponseDTO revokeBan(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityIdNotFoundException("No se encontro el usuario con id: " + userId));
        user.setState(true);
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponseDTO.class);
    }
}
