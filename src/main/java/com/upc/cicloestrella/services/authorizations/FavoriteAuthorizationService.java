package com.upc.cicloestrella.services.authorizations;

import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("favoriteAuthorizationService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FavoriteAuthorizationService implements  AuthorizationServiceInterface {
    private final UserRepository userRepository;

    @Override
    public boolean canAccess(Authentication user, Long studentId) {
        List<String> allowedRoles = List.of("ROLE_MODERATOR", "ROLE_ADMIN");

        if(user.getAuthorities().stream().anyMatch(role -> allowedRoles.contains(role.getAuthority()))){
            return true;
        }

        String username = user.getName();
        Long userId = userRepository.findIdByEmail(username).orElse(null);
        return userId != null && userId.equals(studentId);
    }


}
