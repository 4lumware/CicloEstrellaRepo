package com.upc.cicloestrella.repositories.interfaces;

import com.upc.cicloestrella.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {
}
