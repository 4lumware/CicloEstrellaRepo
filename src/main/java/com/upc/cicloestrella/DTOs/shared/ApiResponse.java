package com.upc.cicloestrella.DTOs.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private int status;
}
