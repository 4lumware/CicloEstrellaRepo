package com.upc.cicloestrella.repositories.interfaces.application.auth;

import com.upc.cicloestrella.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmailAndStateTrue(String email);

    @Query("SELECT u.id FROM User u WHERE u.email = :email and u.state = true")
    Optional<Long> findIdByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    @Query(value = "SELECT r.role_name AS roleName, to_char(u.creation_date, 'YYYY-MM') AS month, COUNT(u.id) AS cnt " + "FROM users u " + "JOIN user_roles ur ON ur.user_id = u.id " + "JOIN roles r ON r.id = ur.role_id " + "WHERE u.state = true " + "AND u.creation_date >= (current_date - interval '1 year') " + "GROUP BY r.role_name, to_char(u.creation_date, 'YYYY-MM') " + "ORDER BY r.role_name, month", nativeQuery = true)
    List<Object[]> countRegistrationsByRolePerMonth();

    @Query(value = "SELECT r.role_name AS roleName, COUNT(u.id) as cnt FROM users u " + "JOIN user_roles ur ON ur.user_id = u.id " + "JOIN roles r ON r.id = ur.role_id " + "WHERE u.state = true " + "GROUP BY r.role_name", nativeQuery = true)
    List<Object[]> countUsersGroupByRole();


    @Query("""
            SELECT COUNT(u.id)
            FROM User u
            WHERE u.creationDate >= :start
              AND u.creationDate < :end
              AND u.state = true
            """)
    Long countRegistrationsBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);


}
