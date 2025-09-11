package com.upc.cicloestrella.interfaces.services;

import com.upc.cicloestrella.DTOs.TeacherRequestDTO;
import com.upc.cicloestrella.DTOs.TeacherResponseDTO;

import java.util.List;

public interface TeacherService {
    public TeacherResponseDTO save(TeacherRequestDTO teacher);
    public List<TeacherRequestDTO> findAll();
    public TeacherResponseDTO findById(Long id);
}
