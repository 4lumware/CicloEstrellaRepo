package com.upc.cicloestrella.specifications.application;

import com.upc.cicloestrella.entities.Formality;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class FormalitySpecification {
    public static Specification<Formality> searchByDescription(String description) {
        return (root, query, cb) -> {
            if (description == null || description.trim().isEmpty()) return null;
            String pattern = "%" + description.toLowerCase().trim() + "%";
            return cb.like(cb.lower(root.get("description")), pattern);
        };
    }

    public static Specification<Formality> searchByTitle(String title) {
        return (root, query, cb) -> {
            if (title == null || title.trim().isEmpty()) return null;
            String pattern = "%" + title.toLowerCase().trim() + "%";
            return cb.like(cb.lower(root.get("title")), pattern);
        };
    }

    public static Specification<Formality> overlapsRange(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (startDate == null && endDate == null) return null;

            if (startDate != null && endDate != null) {
                return cb.and(cb.lessThanOrEqualTo(root.get("startDate"), endDate), cb.greaterThanOrEqualTo(root.get("endDate"), startDate));
            }

            if (startDate != null) {
                return cb.greaterThanOrEqualTo(root.get("endDate"), startDate);
            }

            return cb.lessThanOrEqualTo(root.get("startDate"), endDate);
        };
    }

    public static Specification<Formality> build(String title, String description, LocalDateTime startDate, LocalDateTime endDate) {
        return Specification.<Formality>unrestricted().and(searchByTitle(title)).and(searchByDescription(description)).and(overlapsRange(startDate, endDate));
    }


}
