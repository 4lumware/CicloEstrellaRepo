package com.upc.cicloestrella.repositories.interfaces.auth;

import com.upc.cicloestrella.DTOs.requests.auth.login.UserLoginRequestDTO;
import com.upc.cicloestrella.DTOs.requests.auth.register.UserRegisterRequestDTO;
import com.upc.cicloestrella.DTOs.responses.auth.JWTTokensDTO;
import com.upc.cicloestrella.DTOs.responses.auth.JsonResponseDTO;
import com.upc.cicloestrella.entities.Role;
import com.upc.cicloestrella.enums.RoleByAuthenticationMethods;

import java.io.IOException;

public interface AuthServiceInterface {
    public JsonResponseDTO<?> login(UserLoginRequestDTO userLoginRequestDTO);
    public JsonResponseDTO<?> register(UserRegisterRequestDTO userRegisterRequestDTO , RoleByAuthenticationMethods roleName) throws IOException;
    public JsonResponseDTO<?> logout(String token);
    public JWTTokensDTO refreshToken(String refreshToken);
}
