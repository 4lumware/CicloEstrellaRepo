package com.upc.cicloestrella.repositories;

import com.upc.cicloestrella.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
