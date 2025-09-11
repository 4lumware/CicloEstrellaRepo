package com.upc.cicloestrella.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FormalityDTO {
    private Long idFormality;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
