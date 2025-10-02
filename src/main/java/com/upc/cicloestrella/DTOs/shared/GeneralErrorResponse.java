package com.upc.cicloestrella.DTOs.shared;


import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GeneralErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
