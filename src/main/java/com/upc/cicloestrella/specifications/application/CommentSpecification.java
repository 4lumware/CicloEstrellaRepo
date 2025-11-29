package com.upc.cicloestrella.specifications.application;

import com.upc.cicloestrella.entities.Comment;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class CommentSpecification {
    public static Specification<Comment> textContains(String keyword) {

        return (root, query, cb) -> {
            if (keyword == null || keyword.trim().isEmpty()) return null;
            String pattern = "%" + keyword.toLowerCase().trim() + "%";
            return cb.like(cb.lower(root.get("text")), pattern);
        };
    }

    public static Specification<Comment> studentNameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.trim().isEmpty()) return null;

            var student = root.join("student").join("user");
            String pattern = "%" + name.toLowerCase().trim() + "%";

            return cb.like(cb.lower(student.get("username")), pattern);
        };
    }

    public static Specification<Comment> formalityIdEquals(Long id) {
        return (root, query, cb) -> {
            if (id == null) return null;
            return cb.equal(root.join("formality").get("id"), id);
        };
    }

    public static Specification<Comment> formalityTitleContains(String title) {
        return (root, query, cb) -> {
            if (title == null || title.trim().isEmpty()) return null;
            String pattern = "%" + title.toLowerCase().trim() + "%";
            return cb.like(cb.lower(root.join("formality").get("title")), pattern);
        };
    }

    public static Specification<Comment> createdBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return null;

            if (from != null && to != null)
                return cb.between(root.get("createdAt"), from, to);

            if (from != null)
                return cb.greaterThanOrEqualTo(root.get("createdAt"), from);

            return cb.lessThanOrEqualTo(root.get("createdAt"), to);
        };
    }

    protected static Specification<Comment> studentStateActive() {
        return (root, query, cb) -> {
            var student = root.join("student").join("user");
            return cb.isTrue(student.get("state"));
        };
    }

    public static Specification<Comment> build(String keyword, String studentName, Long formalityId, String formalityTitle, LocalDateTime from, LocalDateTime to) {
        return Specification.<Comment>unrestricted()
                .and(textContains(keyword))
                .and(studentNameContains(studentName))
                .and(formalityIdEquals(formalityId))
                .and(formalityTitleContains(formalityTitle))
                .and(createdBetween(from, to))
                .and(studentStateActive());
    }
}
