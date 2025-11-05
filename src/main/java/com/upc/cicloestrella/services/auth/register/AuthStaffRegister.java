package com.upc.cicloestrella.services.auth.register;

import com.upc.cicloestrella.DTOs.requests.auth.register.StaffRegisterRequestDTO;
import com.upc.cicloestrella.DTOs.responses.StaffResponseDTO;
import com.upc.cicloestrella.DTOs.responses.auth.JWTTokensDTO;
import com.upc.cicloestrella.DTOs.responses.auth.JsonResponseDTO;
import com.upc.cicloestrella.entities.Role;
import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.exceptions.UniqueException;
import com.upc.cicloestrella.interfaces.services.auth.AuthRegisterInterface;
import com.upc.cicloestrella.repositories.interfaces.application.RoleRepository;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import com.upc.cicloestrella.services.auth.AuthService;
import com.upc.cicloestrella.services.auth.JWTService;
import com.upc.cicloestrella.services.auth.RoleService;
import com.upc.cicloestrella.services.logic.ImageCreatorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthStaffRegister implements AuthRegisterInterface<JsonResponseDTO<StaffResponseDTO>, StaffRegisterRequestDTO> {

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JWTService jwtService;
    private final AuthService authService;
    private final ImageCreatorService imageCreatorService;

    @Override
    public JsonResponseDTO<StaffResponseDTO> register(StaffRegisterRequestDTO dto) {

        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new UniqueException("El email " + dto.getEmail() + " ya está en uso.");
        }

        User user = modelMapper.map(dto, User.class);
        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + dto.getRoleId()));

        user.setId(null);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(List.of(role));



        String profileImagePath = null;

        try {

            profileImagePath = generateDefaultProfileImage(dto.getProfilePictureUrl(), user.getUsername());
            user.setProfilePictureUrl(profileImagePath);

        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen de perfil");
        }

        User savedUser = userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        authService.saveUserToken(user, accessToken);

        StaffResponseDTO staffResponseDTO = modelMapper.map(savedUser, StaffResponseDTO.class);

        JWTTokensDTO tokensDTO = JWTTokensDTO.builder()
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .build();


        return JsonResponseDTO.<StaffResponseDTO>builder()
                .user(staffResponseDTO)
                .tokens(tokensDTO)
                .build();

    }

    private String generateDefaultProfileImage(String base64 , String username) throws IOException {

        String prefix = null;
        String data = null;

        if (base64.contains(",")) {
            String[] parts = base64.split(",");
            prefix = parts[0];
            data = parts[1];
        } else {
            prefix = "data:image/png;base64";
            data = base64;
        }
        return imageCreatorService.saveBase64Image(prefix, data, username);
    }
}
