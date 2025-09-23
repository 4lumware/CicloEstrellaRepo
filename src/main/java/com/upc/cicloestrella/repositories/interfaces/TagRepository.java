package com.upc.cicloestrella.repositories.interfaces;

import com.upc.cicloestrella.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {
    List<Tag> findByTagNameContainingIgnoreCase(String tagName);
}
