package com.upc.cicloestrella.repositories.interfaces.application;

import com.upc.cicloestrella.entities.Student;
import com.upc.cicloestrella.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

   Optional<Student> findStudentByUserId(Long userId);

    Long user(User user);
}
