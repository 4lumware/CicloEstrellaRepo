package com.upc.cicloestrella.repositories.interfaces.application;

import com.upc.cicloestrella.entities.Favorite;
import com.upc.cicloestrella.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite , Long>{
    List<Favorite> findAllByStudent_User_Id(Long studentUserId);
    Optional<Favorite> findByIdAndStudent_User_Id(Long id, Long studentUserId);

    boolean existsByStudentAndFavoriteTypeAndReferenceId(Student student, Favorite.FavoriteType type, Long referenceId);
}
