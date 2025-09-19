package com.upc.cicloestrella.repositories.interfaces;

import com.upc.cicloestrella.entities.Campus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampusRepository extends JpaRepository<Campus, Long> {
}
