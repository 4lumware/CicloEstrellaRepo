package com.upc.cicloestrella.repositories.interfaces.auth;

import com.upc.cicloestrella.entities.User;

public interface JWTServiceInterface {
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    String extractUsername(String token);
    boolean isTokenValid(String token, User user);
}
