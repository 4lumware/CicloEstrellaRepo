package com.upc.cicloestrella.repositories.interfaces;


import com.upc.cicloestrella.entities.Formality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FormalityRepository extends JpaRepository<Formality, Long> {
    @Query("SELECT f FROM Formality f " +
            "WHERE LOWER(f.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(f.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Formality> searchByKeyword(@Param("keyword") String keyword);
}
