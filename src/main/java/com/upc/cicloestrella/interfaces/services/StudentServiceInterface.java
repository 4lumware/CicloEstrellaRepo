package com.upc.cicloestrella.interfaces.services;

import com.upc.cicloestrella.DTOs.requests.StudentRequestDTO;
import com.upc.cicloestrella.DTOs.responses.StudentResponseDTO;

import java.util.List;

public interface StudentServiceInterface {
    public StudentResponseDTO save(StudentRequestDTO userRequestDTO);
    public List<StudentResponseDTO> index();
    public StudentResponseDTO show(Long id);
    public StudentResponseDTO update(Long id, StudentRequestDTO userRequestDTO);
    public void delete(Long id);
}
