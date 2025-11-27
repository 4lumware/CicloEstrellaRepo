package com.upc.cicloestrella.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Formalities")
public class Formality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String description;


    @Column(nullable = false , name = "start_date")
    private LocalDate startDate;

    @Column(nullable = false , name="end_date")
    private LocalDate endDate;


    @JsonManagedReference
    @OneToMany(mappedBy = "formality", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
}
