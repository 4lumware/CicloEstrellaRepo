package com.upc.cicloestrella.DTOs.responses.dashboard;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NameValueResponseDTO {
    private String name;
    private double value;
}
