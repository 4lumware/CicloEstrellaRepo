package com.upc.cicloestrella.DTOs.responses.teachers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.upc.cicloestrella.DTOs.responses.CampusResponseDTO;
import com.upc.cicloestrella.DTOs.responses.CareerResponseDTO;
import com.upc.cicloestrella.DTOs.responses.CourseResponseDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeacherJsonResponseConversionDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String generalDescription;
    @JsonProperty("profilePictureURL")
    private String profilePictureUrl;
    private BigDecimal averageRating;
    private List<Long> campusIds;
    private List<Long> careerIds;
    private List<Long> courseIds;
}
