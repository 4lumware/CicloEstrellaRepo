package com.upc.cicloestrella.interfaces.services.auth;

public interface AuthRegisterInterface<T , L> {
    T register(L registerRequestDTO);
}
