package com.upc.cicloestrella.exceptions;


import java.util.Map;

public class ValidationWithFieldsException extends RuntimeException {
    private final Map<String, String> errors;



    public ValidationWithFieldsException(Map<String, String> errors) {
        super("Validacion fallida");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
