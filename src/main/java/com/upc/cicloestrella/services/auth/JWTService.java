package com.upc.cicloestrella.services.auth;

import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.repositories.interfaces.auth.JWTServiceInterface;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JWTService implements JWTServiceInterface {

    @Value("${application.security.jwt.secret-key}")
    private String jwtSecret;
    @Value("${application.security.jwt.expiration}")
    private Long jwtAccessTokenExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long jwtRefreshTokenExpiration;

    @Override
    public String generateAccessToken(User user) {
        return buildToken(user, jwtAccessTokenExpiration);
    }

    @Override
    public String generateRefreshToken(User user) {
        return buildToken(user, jwtRefreshTokenExpiration);
    }

    @Override
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(generateSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public boolean isTokenValid(String token, User user) {
        final String username = extractUsername(token);
        return (username.equals(user.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parser()
                .verifyWith(generateSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    private String buildToken(final User user , final long expiration){
        return Jwts.builder()
                .id(user.getId().toString())
                .subject(user.getEmail())
                .claim("name", user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(generateSignInKey())
                .compact();
    }

    private SecretKey generateSignInKey(){
        byte[] keyBytes = jwtSecret.getBytes();
        return io.jsonwebtoken.security.Keys.hmacShaKeyFor(keyBytes);
    }
}
