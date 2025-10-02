package com.upc.cicloestrella.interfaces.services.application;

import com.upc.cicloestrella.DTOs.requests.TeacherRequestDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherFindByIdResponseDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherResponseDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherSearchByKeywordResponseDTO;
import com.upc.cicloestrella.entities.Teacher;

import java.util.List;

public interface TeacherServiceInterface {
    public TeacherResponseDTO save(TeacherRequestDTO teacher);
    public List<TeacherSearchByKeywordResponseDTO> index(String FirstName);
    public TeacherFindByIdResponseDTO show(Long id);
    public TeacherResponseDTO update(Long id, TeacherRequestDTO teacher);
    public void delete(Long id);
    List<Teacher> searchTeachers(String name, String campus, String course, String career);

}
