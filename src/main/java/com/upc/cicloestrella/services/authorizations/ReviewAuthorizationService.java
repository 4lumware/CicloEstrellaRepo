package com.upc.cicloestrella.services.authorizations;

import com.upc.cicloestrella.repositories.interfaces.application.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("reviewAuthorizationService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReviewAuthorizationService implements AuthorizationServiceInterface{
    private final ReviewRepository reviewRepository;

    @Override
    public boolean canAccess(Authentication user, Long resourceId) {
        if(user.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))){
            return true;
        }

        String username = user.getName();
        return reviewRepository.existsByIdAndStudent_User_Email(resourceId, username);
    }
}
