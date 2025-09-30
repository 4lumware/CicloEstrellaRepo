package com.upc.cicloestrella.interfaces.services.application;

import com.upc.cicloestrella.DTOs.requests.TeacherRequestDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherFindByIdResponseDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherResponseDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherSearchByKeywordResponseDTO;

import java.util.List;

public interface TeacherServiceInterface {
    public TeacherResponseDTO save(TeacherRequestDTO teacher);
    public List<TeacherSearchByKeywordResponseDTO> index(String FirstName);
    public TeacherFindByIdResponseDTO show(Long id);
    public TeacherResponseDTO update(Long id, TeacherRequestDTO teacher);
    public void delete(Long id);
    public List<Teacher> searchByCampuses(String campuses);
    public List<Teacher> searchByCourses(String courses);
    public List<Teacher> searchByCareers(String careers);
}
