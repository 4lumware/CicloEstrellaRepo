package com.upc.cicloestrella.repositories.interfaces.application;

import com.upc.cicloestrella.entities.Student;
import com.upc.cicloestrella.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

   Optional<Student> findStudentByUserId(Long userId);
   Optional<Student> findStudentByUser_Email(String email);
   Optional<Student> findStudentByUser_StateTrueAndUser_Email(String email);

   @Query(value = "SELECT c.career_name, COUNT(sc.student_id) FROM careers c " +
           "LEFT JOIN student_careers sc ON sc.career_id = c.id " +
           "GROUP BY c.career_name", nativeQuery = true)
   List<Object[]> countStudentsByCareer();

    Long user(User user);
}
