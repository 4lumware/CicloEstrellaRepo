package com.upc.cicloestrella.repositories.interfaces.auth;

import com.upc.cicloestrella.DTOs.requests.auth.login.UserLoginRequestDTO;
import com.upc.cicloestrella.DTOs.requests.auth.register.UserRegisterRequestDTO;
import com.upc.cicloestrella.DTOs.responses.auth.JWTTokensDTO;
import com.upc.cicloestrella.DTOs.responses.auth.JsonResponseDTO;
import com.upc.cicloestrella.entities.Role;
import com.upc.cicloestrella.enums.RoleByAuthenticationMethods;

import java.io.IOException;

public interface AuthServiceInterface {
    JsonResponseDTO<?> login(UserLoginRequestDTO userLoginRequestDTO);
    void logout(String token);
    JWTTokensDTO refreshToken(String refreshToken);
}
