package com.upc.cicloestrella.interfaces.services;

import com.upc.cicloestrella.DTOs.TeacherRequestDTO;
import com.upc.cicloestrella.DTOs.TeacherResponseDTO;

import java.util.List;

public interface TeacherServiceInterface {
    public TeacherResponseDTO save(TeacherRequestDTO teacher);
    public List<TeacherResponseDTO> index();
    public TeacherResponseDTO show(Long id);
    public TeacherResponseDTO update(Long id, TeacherRequestDTO teacher);
    public void delete(Long id);
}
