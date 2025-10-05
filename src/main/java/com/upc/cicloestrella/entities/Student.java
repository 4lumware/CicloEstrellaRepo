package com.upc.cicloestrella.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonIgnore
    private User user;

    @Column(nullable = false , name = "current_semester")
    @Positive
    private int currentSemester;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_careers",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "career_id")
    )
    @JsonManagedReference
    private List<Career> careers;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Request> requests;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
}