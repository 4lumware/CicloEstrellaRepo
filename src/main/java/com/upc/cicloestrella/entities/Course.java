package com.upc.cicloestrella.entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String course_name;

    @ManyToMany(mappedBy = "courses")
    private List<Teacher> teacher;
}
