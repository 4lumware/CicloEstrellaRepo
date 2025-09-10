package com.upc.cicloestrella.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100 , nullable = false)
    private String first_name;

    @Column(length = 100 , nullable = false)
    private String last_name;

    @Lob
    @Column(nullable = true)
    private String general_description;

    @Lob
    @Column(nullable = true)
    private String profile_picture_url;

    @Column(precision = 2, scale = 2, nullable = false)
    private BigDecimal average_rating;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "teacher_careers",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "career_id")
    )
    private List<Career> careers;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "teacher_campuses",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "campus_id")
    )
    private List<Campus> campuses;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "teacher_courses",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

}
