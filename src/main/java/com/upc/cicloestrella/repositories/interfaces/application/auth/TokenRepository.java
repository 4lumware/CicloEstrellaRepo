package com.upc.cicloestrella.repositories.interfaces.application.auth;

import com.upc.cicloestrella.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token , Long> {
    @Query("SELECT t FROM Token t WHERE t.user.id = :userId AND (t.isExpired = false OR t.isRevoked = false)")
    List<Token> findAllValidTokenByUserId(Long userId);

    Optional<Token> findByToken(String token);
}
