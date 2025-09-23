package com.upc.cicloestrella.repositories.interfaces;

import com.upc.cicloestrella.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {
    List<Tag> findByTagNameContainingIgnoreCase(String tagName);

    @Query(value = """
        SELECT tg.* 
        FROM tags tg
        JOIN review_tags rt ON tg.id = rt.tag_id
        JOIN review r ON r.id = rt.review_id
        WHERE r.teacher_id = :teacherId
        GROUP BY tg.id, tg.tag_name
        ORDER BY COUNT(*) DESC
        LIMIT 4
        """, nativeQuery = true)
    List<Tag> findTop4TagsByTeacher(@Param("teacherId") Long teacherId);
}
