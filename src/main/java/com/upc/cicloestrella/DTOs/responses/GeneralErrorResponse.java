package com.upc.cicloestrella.DTOs.responses;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeneralErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
