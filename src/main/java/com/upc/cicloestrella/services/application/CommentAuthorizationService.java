package com.upc.cicloestrella.services.application;

import com.upc.cicloestrella.repositories.interfaces.application.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("commentAuthorizationService")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommentAuthorizationService {

    private final CommentRepository commentRepository;
    private final List<String> allowedRoles = List.of("ROLE_ADMIN", "ROLE_MODERATOR");

    public boolean canCreate(Authentication authentication, Long formalityId) {
        return authentication != null && authentication.isAuthenticated();
    }

    public boolean canAccess(Authentication authentication, Long commentId) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        if(authentication.getAuthorities().stream().anyMatch(a -> allowedRoles.contains(a.getAuthority()))) {
            return true;
        }
        String username = authentication.getName();

        return commentRepository.existsByStudent_User_EmailAndStudent_User_StateTrueAndId(username, commentId);
    }
}

