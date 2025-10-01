package com.upc.cicloestrella.repositories.interfaces.auth;

import com.upc.cicloestrella.DTOs.requests.auth.register.UserRegisterRequestDTO;

public interface RoleRegisterInterface<T extends UserRegisterRequestDTO , R> {
    R register(T dto);
}
