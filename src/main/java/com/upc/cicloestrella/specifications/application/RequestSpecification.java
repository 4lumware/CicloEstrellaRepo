package com.upc.cicloestrella.specifications.application;

import com.upc.cicloestrella.entities.Request;
import com.upc.cicloestrella.enums.RequestTypeEnum;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class RequestSpecification {
    public static Specification<Request> hasStatus(Request.RequestStatus status) {
        return (root, query, cb) ->
                status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Request> hasType(RequestTypeEnum type) {
        return (root, query, cb) ->
                type == null ? null : cb.equal(root.get("requestType"), type);
    }

    public static Specification<Request> studentNameContains(String studentName) {
        return (root, query, cb) -> {
            if (studentName == null || studentName.trim().isEmpty()) return null;

            String pattern = "%" + studentName.toLowerCase().trim() + "%";

            var student = root.join("student").join("user");

            return cb.like(cb.lower(student.get("username")), pattern);
        };
    }

    public static Specification<Request> createdAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (startDate == null && endDate == null) return null;

            if (startDate != null && endDate != null)
                return cb.between(root.get("createdAt"), startDate, endDate);

            if (startDate != null)
                return cb.greaterThanOrEqualTo(root.get("createdAt"), startDate);

            return cb.lessThanOrEqualTo(root.get("createdAt"), endDate);
        };
    }


    public static Specification<Request> build(Request.RequestStatus status , RequestTypeEnum type , String studentName , LocalDateTime startDate , LocalDateTime endDate) {
        return Specification.<Request>unrestricted()
                .and(hasStatus(status))
                .and(hasType(type))
                .and(studentNameContains(studentName))
                .and(createdAtBetween(startDate, endDate));
    }
}
