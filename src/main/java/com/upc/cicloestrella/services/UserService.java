package com.upc.cicloestrella.services;


import com.upc.cicloestrella.DTOs.TeacherRequestDTO;
import com.upc.cicloestrella.DTOs.UserRequestDTO;
import com.upc.cicloestrella.DTOs.UserResponseDTO;
import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.interfaces.services.UserServiceInterface;
import com.upc.cicloestrella.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public UserResponseDTO save(UserRequestDTO userRequestDTO) {
        User userEntity = modelMapper.map(userRequestDTO, User.class);
        userEntity.setState(true);
        userEntity.setCreationDate(LocalDate.now());

        User savedUser = userRepository.save(userEntity);

        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    @Override
    public List<UserResponseDTO> index() {
        return userRepository.findAll()
                .stream()
                .filter(User::getState)
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .toList();
    }

    @Override
    public UserResponseDTO show(Long id) {
        return userRepository.findById(id)
                .filter(User::getState)
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .orElse(null);
    }

    @Override
    public UserResponseDTO update(Long id, UserRequestDTO userRequestDTO) {
        return userRepository.findById(id)
                .filter(User::getState)
                .map(existingUser -> {
                    modelMapper.map(userRequestDTO, existingUser);
                    User updatedUser = userRepository.save(existingUser);
                    return modelMapper.map(updatedUser, UserResponseDTO.class);
                })
                .orElse(null);
    }

    @Transactional
    @Override
    public void delete(Long id){

        userRepository.findById(id)
                .ifPresent(user -> {
                    user.setState(false);
                    userRepository.save(user);
       });
    }
}
