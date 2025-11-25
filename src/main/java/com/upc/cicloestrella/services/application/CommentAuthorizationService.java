package com.upc.cicloestrella.services.application;

import com.upc.cicloestrella.repositories.interfaces.application.CommentRepository;
import com.upc.cicloestrella.services.authorizations.AuthorizationServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("commentAuthorizationService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommentAuthorizationService implements AuthorizationServiceInterface {

    private final CommentRepository commentRepository;
    private final List<String> allowedRoles = List.of("ROLE_ADMIN", "ROLE_MODERATOR");

    public boolean canCreate(Authentication authentication, Long formalityId) {
        return authentication != null && authentication.isAuthenticated();
    }

    public boolean canAccess(Authentication authentication, Long commentId) {
        log.info("Authorizing access to comment with ID: {}", commentId);
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        if(authentication.getAuthorities().stream().anyMatch(a -> allowedRoles.contains(a.getAuthority()))) {
            return true;
        }
        String username = authentication.getName();

        return commentRepository
                .existsByIdAndStudent_User_EmailAndStudent_User_StateTrue(commentId, username);
    }
}

