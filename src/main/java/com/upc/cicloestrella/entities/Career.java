package com.upc.cicloestrella.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "careers")
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String careerName;

    @ManyToMany(mappedBy = "careers")
    private List<Teacher> teachers;

    @ManyToMany(mappedBy = "careers")
    private List<Student> students;

}
