package com.upc.cicloestrella.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER ,  orphanRemoval = true)
    @MapsId
    @JoinColumn(name = "user_id" , unique = true, nullable = false, updatable = false)
    private User user;

    @Column(nullable = false)
    private int currentSemester;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_careers",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "career_id")
    )
    private List<Career> careers;


}