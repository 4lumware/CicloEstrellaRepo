package com.upc.cicloestrella.DTOs.responses;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentResponseDTO {
    private Long id;
    private String username;
    private String email;
    private int currentSemester;
    private String profilePictureUrl;
    private LocalDate creationDate;
    private List<CareerResponseDTO> careers;
}
