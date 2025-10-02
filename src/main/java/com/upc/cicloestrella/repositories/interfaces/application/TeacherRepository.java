package com.upc.cicloestrella.repositories.interfaces.application;


import com.upc.cicloestrella.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    public List<Teacher> findByFirstNameContainingIgnoreCase(String firstName);

    @Query(value = "SELECT DISTINCT t.* FROM teachers t " +
            "LEFT JOIN teacher_campuses tca ON t.id = tca.teacher_id " +
            "LEFT JOIN campuses ca ON ca.id = tca.campus_id " +
            "LEFT JOIN teacher_courses tco ON t.id = tco.teacher_id " +
            "LEFT JOIN courses co ON co.id = tco.course_id " +
            "LEFT JOIN teacher_careers tcar ON t.id = tcar.teacher_id " +
            "LEFT JOIN careers car ON car.id = tcar.career_id " +
            "WHERE (:name IS NULL OR t.first_name LIKE %:name% OR t.last_name LIKE %:name%) " +
            "AND (:campus IS NULL OR ca.campus_name LIKE %:campus%) " +
            "AND (:course IS NULL OR co.course_name LIKE %:course%) " +
            "AND (:career IS NULL OR car.career_name LIKE %:career%)",
            nativeQuery = true)
    List<Teacher> searchTeachers(@Param("name") String name,
                                 @Param("campus") String campus,
                                 @Param("course") String course,
                                 @Param("career") String career);


}
