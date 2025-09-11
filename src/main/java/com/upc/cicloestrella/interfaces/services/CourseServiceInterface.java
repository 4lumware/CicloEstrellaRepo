package com.upc.cicloestrella.interfaces.services;


import com.upc.cicloestrella.DTOs.CourseRequestDTO;
import com.upc.cicloestrella.DTOs.CourseResponseDTO;

import java.util.List;

public interface CourseServiceInterface {
    public List<CourseResponseDTO> index();
    public CourseResponseDTO show(Long id);
    public CourseResponseDTO save(CourseRequestDTO format);
    public CourseResponseDTO update(Long id, CourseRequestDTO format);
    public void delete(Long id);

}
