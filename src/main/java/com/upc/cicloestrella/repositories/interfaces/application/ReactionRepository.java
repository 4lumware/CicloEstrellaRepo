package com.upc.cicloestrella.repositories.interfaces.application;

import com.upc.cicloestrella.entities.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReactionRepository extends JpaRepository<Reaction , Long> {
    List<Reaction> findByReactionNameContainingIgnoreCase(String name);
}
