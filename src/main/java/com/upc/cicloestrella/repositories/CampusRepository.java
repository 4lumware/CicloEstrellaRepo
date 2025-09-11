package com.upc.cicloestrella.repositories;

import com.upc.cicloestrella.entities.Campus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampusRepository extends JpaRepository<Campus, Long> {
}
