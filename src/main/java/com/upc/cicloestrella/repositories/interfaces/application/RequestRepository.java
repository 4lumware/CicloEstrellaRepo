package com.upc.cicloestrella.repositories.interfaces.application;

import com.upc.cicloestrella.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request,Long> {
}
