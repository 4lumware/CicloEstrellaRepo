package com.upc.cicloestrella.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ValidationErrorResponse {
    private int status;
    private String message;
    private Map<String,String> errors;
    private LocalDateTime timestamp;
}
