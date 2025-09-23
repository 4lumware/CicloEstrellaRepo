package com.upc.cicloestrella.repositories.interfaces;


import com.upc.cicloestrella.entities.Tag;
import com.upc.cicloestrella.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    public List<Teacher> findByFirstNameContainingIgnoreCase(String firstName);

    @Query(value = "SELECT t.* FROM teachers t\n" +
            "INNER JOIN teacher_campuses tc ON t.id = tc.teacher_id\n"+
            "INNER JOIN campuses c ON c.id = tc.campus_id\n"+
            "WHERE c.campus_name like %:campuses%",nativeQuery = true)
    public List<Teacher> searchByCampuses(@Param("campuses") String campuses);


    @Query(value = "SELECT t.* FROM teachers t\n" +
            "INNER JOIN teacher_courses tc ON t.id = tc.teacher_id\n"+
            "INNER JOIN courses c ON c.id = tc.course_id\n"+
            "WHERE c.course_name like %:courses%",nativeQuery = true)
    public List<Teacher> searchByCourses(@Param("courses") String courses);


    @Query(value = "SELECT t.* FROM teachers t\n" +
            "INNER JOIN teacher_careers tc ON t.id = tc.teacher_id\n"+
            "INNER JOIN careers c ON c.id = tc.career_id\n"+
            "WHERE c.career_name like %:careers%",nativeQuery = true)
    public List<Teacher> searchByCareers(@Param("careers") String careers);

}
