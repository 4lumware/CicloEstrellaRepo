package com.upc.cicloestrella.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.databind.JsonNode;
import com.upc.cicloestrella.enums.RequestTypeEnum;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "requests")
public class Request {

    public enum RequestStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private RequestTypeEnum requestType;

        @Type(JsonType.class)
        @Column(columnDefinition = "jsonb", nullable = false)
        private JsonNode content;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private RequestStatus status;

        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @PrePersist
        protected void onCreate() {
            createdAt = LocalDateTime.now();
            if (status == null) {
                status = RequestStatus.PENDING;
            }
        }
}
