package com.upc.cicloestrella.repositories.interfaces.application;

import com.upc.cicloestrella.entities.Favorite;
import com.upc.cicloestrella.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite , Long>{
    List<Favorite> findAllByStudent(Student student);
    boolean existsByStudentAndFavoriteTypeAndReferenceId(Student student, Favorite.FavoriteType type, Long referenceId);
}
