package com.upc.cicloestrella.repositories.interfaces.application;

import com.upc.cicloestrella.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request,Long> {

    Optional<Request> findByIdAndStudent_User_Id(Long requestId, Long studentId);
    List<Request> findRequestsByStudent_User_Id(Long studentId);
}
