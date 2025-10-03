package com.upc.cicloestrella.services.authorizations;

import com.upc.cicloestrella.repositories.interfaces.application.ReviewReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("reviewReactionAuthorizationService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReviewReactionAuthorizationService implements AuthorizationServiceInterface{
    private final ReviewReactionRepository reviewReactionRepository;

    @Override
    public boolean canAccess(Authentication user, Long resourceId) {
        List<String> allowedRoles = List.of("ROLE_MODERATOR", "ROLE_ADMIN");

        if(user.getAuthorities().stream().anyMatch(role -> allowedRoles.contains(role.getAuthority()))){
            return true;
        }

        String username = user.getName();
        return reviewReactionRepository.existsReviewReactionByIdAndAuthor_User_Email(resourceId, username);
    }
}
