package com.upc.cicloestrella.services.authorizations;

import com.upc.cicloestrella.repositories.interfaces.application.RequestRepository;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("requestAuthorizationService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestAuthorizationService implements AuthorizationServiceInterface{
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    @Override
    public boolean canAccess(Authentication user, Long resourceId) {
        List<String> allowedRoles = List.of("ROLE_MODERATOR", "ROLE_ADMIN");

        if(user.getAuthorities().stream().anyMatch(role -> allowedRoles.contains(role.getAuthority()))){
            return true;
        }
        String username = user.getName();
        Long userId = userRepository.findIdByEmail(username).orElse(null);
        return userId != null && userId.equals(resourceId);
    }
}
