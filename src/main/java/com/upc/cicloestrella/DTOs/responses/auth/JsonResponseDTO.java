package com.upc.cicloestrella.DTOs.responses.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JsonResponseDTO<T>
{
    private T user;
    private JWTTokensDTO tokens;
}
