package com.upc.cicloestrella.repositories;

import com.upc.cicloestrella.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
