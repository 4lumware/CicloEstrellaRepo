package com.upc.cicloestrella.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "careers")
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String careerName;

    @ManyToMany(mappedBy = "careers")
    private List<Teacher> teachers;

    @ManyToMany(mappedBy = "careers")
    private List<Student> students;


}
