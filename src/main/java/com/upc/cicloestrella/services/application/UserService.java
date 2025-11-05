package com.upc.cicloestrella.services.application;


import com.upc.cicloestrella.DTOs.requests.UserRequestDTO;
import com.upc.cicloestrella.DTOs.responses.UserResponseDTO;
import com.upc.cicloestrella.entities.Role;
import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.exceptions.UniqueException;
import com.upc.cicloestrella.interfaces.services.application.UserServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.application.RoleRepository;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import com.upc.cicloestrella.services.logic.ImageCreatorService;
import com.upc.cicloestrella.specifications.application.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ImageCreatorService imageCreatorService;
    private final RoleRepository roleRepository;


    @Override
    public UserResponseDTO findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityIdNotFoundException("No se encontro el usuario con id: " + userId));
        return modelMapper.map(user, UserResponseDTO.class);
    }



    @Override
    public UserResponseDTO delete(Long userId) throws IOException, URISyntaxException {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityIdNotFoundException("No se encontro el usuario con id: " + userId));
        user.setState(false);
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO update(Long userId, UserRequestDTO user) throws IOException {


        if(userRepository.existsByEmailAndIdNot(user.getEmail() , userId)) {
            throw new UniqueException("El email " + user.getEmail() + " ya está en uso.");
        }


        User existingUser = userRepository.findById(userId).orElseThrow(() -> new EntityIdNotFoundException("No se encontro el usuario con id: " + userId));
        List<Long> roleIds = Collections.singletonList(user.getRoleId());
        List<Role> roles = roleRepository.findAllById(roleIds);

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setRoles(roles);

        if(!user.getProfilePictureUrl().equals(existingUser.getProfilePictureUrl())){
            String prefix = user.getProfilePictureUrl().split(",")[0];
            String data = user.getProfilePictureUrl().split(",")[1];
            existingUser.setProfilePictureUrl(imageCreatorService.saveBase64Image(prefix , data , existingUser.getUsername()));
        }
        else {
            existingUser.setProfilePictureUrl(user.getProfilePictureUrl());
        }


        User updatedUser = userRepository.save(existingUser);
        return modelMapper.map(updatedUser, UserResponseDTO.class);
    }


    @Override
    public Page<UserResponseDTO> index(String username, String roleName, Boolean state, LocalDate startDate, LocalDate endDate , int page , int size) {
        Specification<User> spec = UserSpecification.build(username, roleName, state, startDate, endDate);
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(spec, pageable);
        return usersPage.map(user -> modelMapper.map(user, UserResponseDTO.class));
    }


    public UserResponseDTO revokeBan(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityIdNotFoundException("No se encontro el usuario con id: " + userId));
        user.setState(true);
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponseDTO.class);
    }
}
