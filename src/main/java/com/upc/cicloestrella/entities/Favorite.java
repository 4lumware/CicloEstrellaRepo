package com.upc.cicloestrella.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "favorites")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Favorite {
    @Id
    private Long id;
    public enum FavoriteType {
        TEACHER,
        FORMALITY
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(name = "favorite_type", nullable = false)
    private FavoriteType favoriteType;

    @Column(name = "reference_id", nullable = false)
    private Long referenceId;
}
