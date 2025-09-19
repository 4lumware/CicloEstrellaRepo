package com.upc.cicloestrella.repositories.interfaces;


import com.upc.cicloestrella.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    public List<Teacher> findByFirstNameContainingIgnoreCase(String firstName);
}
