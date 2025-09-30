package com.upc.cicloestrella.repositories.interfaces.application;

import com.upc.cicloestrella.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
