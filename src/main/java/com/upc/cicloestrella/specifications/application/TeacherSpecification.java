package com.upc.cicloestrella.specifications.application;

import com.upc.cicloestrella.entities.Teacher;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;

public class TeacherSpecification {
    public static Specification<Teacher> fullNameContains(String fullName) {
        return (root, query, cb) -> {
            if (fullName == null || fullName.trim().isEmpty()) return null;
            String pattern = "%" + fullName.toLowerCase().trim() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("firstName")), pattern),
                    cb.like(cb.lower(root.get("lastName")), pattern),
                    cb.like(cb.lower(cb.concat(root.get("firstName"), cb.concat(" ", root.get("lastName")))), pattern)
            );
        };
    }

    public static Specification<Teacher> ratingBetween(BigDecimal minRating, BigDecimal maxRating) {
        return (root, query, cb) -> {
            if (minRating == null && maxRating == null) return null;
            if (minRating != null && maxRating != null) {
                return cb.between(root.get("averageRating"), minRating, maxRating);
            } else if (minRating != null) {
                return cb.greaterThanOrEqualTo(root.get("averageRating"), minRating);
            } else {
                return cb.lessThanOrEqualTo(root.get("averageRating"), maxRating);
            }
        };
    }

    public static Specification<Teacher> careersIn(List<Long> careerIds) {
        return (root, query, cb) -> {
            if (careerIds == null || careerIds.isEmpty()) return null;
            var join = root.join("careers");
            return join.get("id").in(careerIds);
        };
    }

    public static Specification<Teacher> coursesIn(List<Long> courseIds) {
        return (root, query, cb) -> {
            if (courseIds == null || courseIds.isEmpty()) return null;
            var join = root.join("courses");
            return join.get("id").in(courseIds);
        };
    }

    public static Specification<Teacher> campusesIn(List<Long> campusIds) {
        return (root, query, cb) -> {
            if (campusIds == null || campusIds.isEmpty()) return null;
            var join = root.join("campuses");
            return join.get("id").in(campusIds);
        };
    }

    public static Specification<Teacher> build(String fullName,
                                               BigDecimal minRating,
                                               BigDecimal maxRating,
                                               List<Long> careerIds,
                                               List<Long> courseIds,
                                               List<Long> campusIds) {
        return Specification.<Teacher>unrestricted()
                .and(fullNameContains(fullName))
                .and(ratingBetween(minRating, maxRating))
                .and(careersIn(careerIds))
                .and(coursesIn(courseIds))
                .and(campusesIn(campusIds));
    }
}
