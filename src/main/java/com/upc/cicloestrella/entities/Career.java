package com.upc.cicloestrella.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Career {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameCareer;

    @ManyToMany(mappedBy = "careers")
    private List<Teacher> teachers;

    @ManyToMany(mappedBy = "careers")
    private List<Student> students;


}
