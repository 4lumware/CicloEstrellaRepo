package com.upc.cicloestrella.exceptions;

import com.upc.cicloestrella.DTOs.responses.GeneralErrorResponse;
import com.upc.cicloestrella.DTOs.responses.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity
                .badRequest()
                .body(new ValidationErrorResponse(400, "Validation Failed", errors, LocalDateTime.now()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GeneralErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        log.error("Database constraint violation: {}", ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new GeneralErrorResponse(400, "Database Constraint Violation", LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralErrorResponse> handleAllExceptions(Exception ex){
        log.error("An unexpected error occurred: {}", ex.getMessage());
        return ResponseEntity
                .status(500)
                .body(new GeneralErrorResponse(500, "An unexpected error occurred", LocalDateTime.now()));
    }


}
