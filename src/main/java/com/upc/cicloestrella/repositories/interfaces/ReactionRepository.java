package com.upc.cicloestrella.repositories.interfaces;

import com.upc.cicloestrella.entities.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction , Long> {
}
