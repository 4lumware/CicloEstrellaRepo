package com.upc.cicloestrella.specifications.application;

import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.entities.Role;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;



public class UserSpecification {
    public static Specification<User> usernameContains(String username) {
        return (root, query, cb) -> {
            if (username == null || username.isEmpty()) return null;
            return cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase() + "%");
        };
    }

    public static Specification<User> hasRole(String roleName) {
        return (root, query, cb) -> {
            if (roleName == null || roleName.isEmpty()) {
                return cb.conjunction(); // no filtra nada
            }
            Join<User, Role> roles = root.join("roles", JoinType.INNER);
            return cb.equal(cb.lower(roles.get("roleName").as(String.class)), roleName.toLowerCase());
        };
    }

    public static Specification<User> hasState(Boolean state) {
        return (root, query, cb) -> {
            if (state == null) return null;
            return cb.equal(root.get("state"), state);
        };
    }

    public static Specification<User> creationDateBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) -> {
            if (start == null && end == null) return null;
            if (start != null && end != null) {
                return cb.between(root.get("creationDate"), start, end);
            } else if (start != null) {
                return cb.greaterThanOrEqualTo(root.get("creationDate"), start);
            } else {
                return cb.lessThanOrEqualTo(root.get("creationDate"), end);
            }
        };
    }

    public static Specification<User> build(String username, String roleName, Boolean state, LocalDate startDate, LocalDate endDate) {
        return Specification.<User>unrestricted()
                .and(usernameContains(username))
                .and(hasRole(roleName))
                .and(hasState(state))
                .and(creationDateBetween(startDate, endDate));
    }
}
