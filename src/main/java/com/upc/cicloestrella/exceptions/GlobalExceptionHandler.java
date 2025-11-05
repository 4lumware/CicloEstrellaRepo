package com.upc.cicloestrella.exceptions;

import com.upc.cicloestrella.DTOs.shared.GeneralErrorResponse;
import com.upc.cicloestrella.DTOs.shared.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                .body(new ValidationErrorResponse(400, "Validacion fallida", errors, LocalDateTime.now()));
    }

    @ExceptionHandler(UniqueException.class)
    public ResponseEntity<GeneralErrorResponse> handleUniqueException(UniqueException ex){
        log.error("Unique constraint violation: {}", ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new GeneralErrorResponse(400,  ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<GeneralErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException ex){
        log.error("Authorization denied: {}", ex.getMessage());
        return ResponseEntity
                .status(403)
                .body(new GeneralErrorResponse(403, "No tienes permiso para realizar esta accion", LocalDateTime.now()));
    }

    @ExceptionHandler(ValidationWithFieldsException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(ValidationWithFieldsException ex) {
        return ResponseEntity
                .badRequest()
                .body(new ValidationErrorResponse(
                        400,
                        ex.getMessage(),
                        ex.getErrors(),
                        LocalDateTime.now()
                ));
    }



    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GeneralErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        log.error("Database constraint violation: {}", ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new GeneralErrorResponse(400, "Violacion de restriccion de la base de datos", LocalDateTime.now()));
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<GeneralErrorResponse> handleSecurityException(SecurityException ex){
        log.error("Security exception: {}", ex.getMessage());
        return ResponseEntity
                .status(403)
                .body(new GeneralErrorResponse(403, ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<GeneralErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex){
        log.error("Username not found: {}", ex.getMessage());
        return ResponseEntity
                .status(404)
                .body(new GeneralErrorResponse(404, ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(EntityIdNotFoundException.class)
    public ResponseEntity<GeneralErrorResponse> handleEntityNotFoundException(EntityIdNotFoundException ex){
        log.error("Entity not found: {}", ex.getMessage());
        return ResponseEntity
                .status(404)
                .body(new GeneralErrorResponse(404, ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GeneralErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex){
        log.error("Illegal argument: {}", ex.getMessage());
        return ResponseEntity
                .badRequest()
                .body(new GeneralErrorResponse(400, ex.getMessage(), LocalDateTime.now()));
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<GeneralErrorResponse> handleBadCredentialsException(BadCredentialsException ex){
        log.error("Bad credentials: {}", ex.getMessage());
        return ResponseEntity
                .status(401)
                .body(new GeneralErrorResponse(401, "Credenciales incorrectas", LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralErrorResponse> handleAllExceptions(Exception ex){

        if (ex instanceof AccessDeniedException || ex instanceof AuthenticationException) {
            throw (RuntimeException) ex; // dejar que Spring Security lo maneje
        }

        log.error("An unexpected error occurred: {}", ex.getMessage());
        return ResponseEntity
                .status(500)
                .body(new GeneralErrorResponse(500, "Un inesperado error ha sucedido", LocalDateTime.now()));
    }


}
