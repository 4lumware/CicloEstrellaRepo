package com.upc.cicloestrella.services.auth;

import com.upc.cicloestrella.DTOs.requests.auth.register.StaffRegisterRequestDTO;
import com.upc.cicloestrella.entities.Role;
import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import com.upc.cicloestrella.repositories.interfaces.auth.RoleRegisterInterface;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ =  @Autowired)

public class StaffRegisterService implements RoleRegisterInterface<StaffRegisterRequestDTO , User> {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public User register(StaffRegisterRequestDTO dto) {
        User user = modelMapper.map(dto, User.class);
        return userRepository.save(user);
    }
}
