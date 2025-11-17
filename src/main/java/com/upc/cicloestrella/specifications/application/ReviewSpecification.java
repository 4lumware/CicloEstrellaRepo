package com.upc.cicloestrella.specifications.application;

import com.upc.cicloestrella.entities.Review;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ReviewSpecification {

    public static Specification<Review> descriptionContains(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.trim().isEmpty()) return null;
            String pattern = "%" + keyword.toLowerCase().trim() + "%";
            return cb.like(cb.lower(root.get("description")), pattern);
        };
    }

    public static Specification<Review> studentIdEquals(Long studentId) {
        return (root, query, cb) -> {
            if (studentId == null) return null;
            return cb.equal(root.join("student").get("id"), studentId);
        };
    }

    public static Specification<Review> teacherIdEquals(Long teacherId) {
        return (root, query, cb) -> {
            if (teacherId == null) return null;
            return cb.equal(root.join("teacher").get("id"), teacherId);
        };
    }

    public static Specification<Review> teacherNameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) return null;
            var teacher = root.join("teacher");
            String pattern = "%" + name.toLowerCase().trim() + "%";
            return cb.or(
                    cb.like(cb.lower(teacher.get("firstName")), pattern),
                    cb.like(cb.lower(teacher.get("lastName")), pattern)
            );
        };
    }

    public static Specification<Review> studentNameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) return null;
            var student = root.join("student").join("user");
            String pattern = "%" + name.toLowerCase().trim() + "%";
            return cb.like(cb.lower(student.get("username")), pattern);
        };
    }

    public static Specification<Review> ratingBetween(BigDecimal minRating, BigDecimal maxRating) {
        return (root, query, cb) -> {
            if (minRating == null && maxRating == null) return null;

            if (minRating != null && maxRating != null) {
                return cb.between(root.get("rating"), minRating, maxRating);
            } else if (minRating != null) {
                return cb.greaterThanOrEqualTo(root.get("rating"), minRating);
            } else {
                return cb.lessThanOrEqualTo(root.get("rating"), maxRating);
            }
        };
    }

    public static Specification<Review> hasTag(Long tagId) {
        return (root, query, cb) -> {
            if (tagId == null) return null;
            var tags = root.join("tags");
            return cb.equal(tags.get("id"), tagId);
        };
    }

    public static Specification<Review> hasTagName(String tagName) {
        return (root, query, cb) -> {
            if (tagName == null || tagName.trim().isEmpty()) return null;
            var tags = root.join("tags");
            String pattern = "%" + tagName.toLowerCase().trim() + "%";
            return cb.like(cb.lower(tags.get("name")), pattern);
        };
    }

    public static Specification<Review> createdBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return null;

            if (from != null && to != null)
                return cb.between(root.get("createdAt"), from, to);

            if (from != null)
                return cb.greaterThanOrEqualTo(root.get("createdAt"), from);

            return cb.lessThanOrEqualTo(root.get("createdAt"), to);
        };
    }

    public static Specification<Review> build(
            String keyword,
            Long studentId,
            Long teacherId,
            String teacherName,
            String studentName,
            BigDecimal minRating,
            BigDecimal maxRating,
            Long tagId,
            String tagName,
            LocalDateTime from,
            LocalDateTime to) {
        return Specification.<Review>unrestricted()
                .and(descriptionContains(keyword))
                .and(studentIdEquals(studentId))
                .and(teacherIdEquals(teacherId))
                .and(teacherNameContains(teacherName))
                .and(studentNameContains(studentName))
                .and(ratingBetween(minRating, maxRating))
                .and(hasTag(tagId))
                .and(hasTagName(tagName))
                .and(createdBetween(from, to));
    }
}

