package com.upc.cicloestrella.validators;

import com.upc.cicloestrella.exceptions.ValidationWithFieldsException;
import jakarta.validation.ValidationException;

public interface ValidatorInterface<T,L> {
    L validate(T obj) throws ValidationWithFieldsException;
}
