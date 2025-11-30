package com.upc.cicloestrella.services.authorizations;

import com.upc.cicloestrella.repositories.interfaces.application.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("reviewAuthorizationService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReviewAuthorizationService implements AuthorizationServiceInterface{
    private final ReviewRepository reviewRepository;
    private final List<String> STAFF_ROLES = List.of("ROLE_ADMIN" , "ROLE_MODERATOR");
    @Override
    public boolean canAccess(Authentication user, Long resourceId) {
        if(user.getAuthorities().stream().anyMatch(role -> STAFF_ROLES.contains(role.getAuthority()))){
            return true;
        }

        String username = user.getName();
        return reviewRepository.existsByIdAndStudent_User_Email(resourceId, username);
    }
}
