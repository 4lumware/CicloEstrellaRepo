package com.upc.cicloestrella.DTOs.responses.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JWTTokensDTO {
    private String access_token;
    private String refresh_token;
}
