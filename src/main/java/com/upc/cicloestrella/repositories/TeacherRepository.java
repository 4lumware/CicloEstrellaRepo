package com.upc.cicloestrella.repositories;


import com.upc.cicloestrella.DTOs.TeacherResponseDTO;
import com.upc.cicloestrella.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    public List<Teacher> findByFirstNameContainingIgnoreCase(String firstName);
}
