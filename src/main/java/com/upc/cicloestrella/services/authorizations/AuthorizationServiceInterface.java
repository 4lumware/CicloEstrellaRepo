package com.upc.cicloestrella.services.authorizations;

import org.springframework.security.core.Authentication;

public interface AuthorizationServiceInterface {
    boolean canAccess(Authentication user, Long resourceId);
}
