package com.upc.cicloestrella.DTOs.responses.teachers;

import com.upc.cicloestrella.DTOs.responses.CampusResponseDTO;
import com.upc.cicloestrella.DTOs.responses.CareerResponseDTO;
import com.upc.cicloestrella.DTOs.responses.CourseResponseDTO;
import com.upc.cicloestrella.DTOs.responses.TagResponseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TeacherFindByIdResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String generalDescription;
    private String profilePictureUrl;
    private BigDecimal averageRating;
    private List<TagResponseDTO> tags;
    private List<CampusResponseDTO> campuses;
    private List<CareerResponseDTO> careers;
    private List<CourseResponseDTO> courses;
}
