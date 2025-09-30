package com.upc.cicloestrella.repositories.interfaces.application;

import com.upc.cicloestrella.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
