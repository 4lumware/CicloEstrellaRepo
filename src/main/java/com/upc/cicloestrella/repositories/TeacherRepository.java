package com.upc.cicloestrella.repositories;


import com.upc.cicloestrella.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
