package com.upc.cicloestrella.services.auth;

import com.upc.cicloestrella.DTOs.requests.auth.login.UserLoginRequestDTO;
import com.upc.cicloestrella.DTOs.responses.StaffResponseDTO;
import com.upc.cicloestrella.DTOs.responses.auth.JWTTokensDTO;
import com.upc.cicloestrella.DTOs.responses.auth.JsonResponseDTO;
import com.upc.cicloestrella.entities.Role;
import com.upc.cicloestrella.entities.Student;
import com.upc.cicloestrella.entities.Token;
import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.mappers.StudentMapper;
import com.upc.cicloestrella.repositories.interfaces.application.StudentRepository;
import com.upc.cicloestrella.repositories.interfaces.application.auth.TokenRepository;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import com.upc.cicloestrella.repositories.interfaces.auth.AuthServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.auth.JWTServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
public class AuthService implements AuthServiceInterface {
    private final JWTServiceInterface jwtService;
    private final TokenRepository tokenRepository;
    private final ModelMapper modelMapper;
    private final StudentMapper studentMapper;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    @Override
    public JsonResponseDTO<Object> login(UserLoginRequestDTO userLoginRequestDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequestDTO.getEmail(),
                        userLoginRequestDTO.getPassword()
                )
        );

        User user = userRepository.findByEmailAndStateTrue(userLoginRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("El usuario con email " + userLoginRequestDTO.getEmail() + " no existe"));

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        Object dto = getUserDTOByRole(user);

        return JsonResponseDTO.builder()
                .user(dto)
                .tokens(JWTTokensDTO.builder().access_token(accessToken).refresh_token(refreshToken).build())
                .build();
    }

    private Object getUserDTOByRole(User user) {
        for (Role role : user.getRoles()) {
            switch (role.getRoleName()) {

                case STUDENT -> {

                    Student student = studentRepository.findStudentByUserId(user.getId())
                            .orElseThrow(() -> new UsernameNotFoundException("Estudiante no encontrado para el usuario con ID: " + user.getId()));

                    return studentMapper.toDTO(student);
                }
                case ADMIN , MODERATOR -> {
                    return modelMapper.map(user, StaffResponseDTO.class);
                }
                default -> throw new IllegalArgumentException("Rol no soportado: " + role.getRoleName());
            }
        }
        throw new IllegalArgumentException("El usuario no tiene un rol soportado");

    }


    @Override
    public JWTTokensDTO refreshToken(String authHeader) {
        if(authHeader == null  || !authHeader.startsWith("Bearer ")) throw new IllegalArgumentException("Invalid token header");

        String refreshToken = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(refreshToken);

        if(userEmail == null) throw new IllegalArgumentException("Token invalido");

        final User user = userRepository.findByEmailAndStateTrue(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + userEmail));

        final boolean isValidToken = jwtService.isTokenValid(refreshToken , user);

        if(!isValidToken) throw new IllegalArgumentException("Invalid token");

        final String accessToken = jwtService.generateAccessToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return JWTTokensDTO.builder()
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .build();
    }



    @Override
    public JsonResponseDTO<Object> logout(String token) {
        return null;
    }

    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .type(Token.TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUserId(user.getId());
        if (validUserTokens.isEmpty()) return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }
}
