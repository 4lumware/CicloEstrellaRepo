package com.upc.cicloestrella.specifications.application;

import com.upc.cicloestrella.entities.Comment;
import org.springframework.data.jpa.domain.Specification;

public class CommentSpecification {
    public static Specification<Comment> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isEmpty()) return cb.conjunction();
            return cb.like(cb.lower(root.get("text")), "%" + keyword.toLowerCase() + "%");
        };
    }
}

