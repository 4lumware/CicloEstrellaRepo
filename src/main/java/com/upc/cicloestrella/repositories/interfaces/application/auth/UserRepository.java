package com.upc.cicloestrella.repositories.interfaces.application.auth;

import com.upc.cicloestrella.entities.Role;
import com.upc.cicloestrella.entities.User;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long > , JpaSpecificationExecutor<User> {
    Optional<User> findByEmailAndStateTrue(String email);

    @Query("SELECT u.id FROM User u WHERE u.email = :email and u.state = true")
    Optional<Long> findIdByEmail(@Param("email") String email);




}
