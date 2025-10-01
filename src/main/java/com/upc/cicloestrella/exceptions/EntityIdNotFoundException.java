package com.upc.cicloestrella.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class EntityIdNotFoundException extends UsernameNotFoundException {
    public EntityIdNotFoundException(String message) {
        super(message);
    }
}

