package com.upc.cicloestrella.repositories.interfaces;

import com.upc.cicloestrella.entities.Career;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, Long> {
}
