package com.upc.cicloestrella.entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;

    @ManyToMany(mappedBy = "courses")
    private List<Teacher> teacher;

    @ManyToMany
    @JoinTable(
            name = "course_formats",
            joinColumns = {
                    @JoinColumn(name = "course_id", referencedColumnName = "id"),
            },
            inverseJoinColumns = @JoinColumn(name = "format_id")
    )
    private List<Format> formats;
}
