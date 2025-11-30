package com.upc.cicloestrella.interfaces.services.application;

import com.upc.cicloestrella.DTOs.requests.StudentRequestDTO;
import com.upc.cicloestrella.DTOs.responses.StudentResponseDTO;

import java.io.IOException;
import java.util.List;

public interface StudentServiceInterface {
    public List<StudentResponseDTO> index();
    public StudentResponseDTO show(Long id);
    public StudentResponseDTO update(Long id, StudentRequestDTO userRequestDTO) throws IOException;
    public void delete(Long id);
    StudentResponseDTO updatePassword(Long id, String newPassword , String oldPassword) ;
}
