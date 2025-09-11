package com.upc.cicloestrella.entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "formats")
public class Format {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String formatName;
    @ManyToMany(mappedBy = "courses")
    private List<Course> courses;
}
