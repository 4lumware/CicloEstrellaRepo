package com.upc.cicloestrella.services.auth;

import com.upc.cicloestrella.DTOs.requests.auth.register.StaffRegisterRequestDTO;
import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import com.upc.cicloestrella.repositories.interfaces.auth.RoleRegisterInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ =  @Autowired)

public class StaffRegisterService implements RoleRegisterInterface<StaffRegisterRequestDTO , User> {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;

    @Transactional
    @Override
    public User register(StaffRegisterRequestDTO dto) {
        if (dto.getRoleId() == null) {
            throw new IllegalArgumentException("El campo roleId es obligatorio para registrar un staff.");
        }

        System.out.println("Registering staff with roleId: " + dto.getRoleId());

        User user = modelMapper.map(dto, User.class);
        user.setId(null);
        user.setCreationDate(LocalDate.now());
        user.setState(true);
        User savedUser = userRepository.save(user);

        roleService.assignRoleToUser(savedUser.getId()  ,dto.getRoleId());
        return savedUser;

    }
}
