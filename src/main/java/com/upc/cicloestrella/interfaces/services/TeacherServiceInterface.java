package com.upc.cicloestrella.interfaces.services;

import com.upc.cicloestrella.DTOs.requests.TeacherRequestDTO;
import com.upc.cicloestrella.DTOs.responses.TeacherResponseDTO;
import com.upc.cicloestrella.entities.Teacher;

import java.util.List;

public interface TeacherServiceInterface {
    public TeacherResponseDTO save(TeacherRequestDTO teacher);
    public List<TeacherResponseDTO> index(String FirstName);
    public TeacherResponseDTO show(Long id);
    public TeacherResponseDTO update(Long id, TeacherRequestDTO teacher);
    public void delete(Long id);
    public List<Teacher> searchByCampuses(String campuses);
    public List<Teacher> searchByCourses(String courses);
    public List<Teacher> searchByCareers(String careers);
}
