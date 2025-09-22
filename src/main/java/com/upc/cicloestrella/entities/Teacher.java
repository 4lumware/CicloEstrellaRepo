package com.upc.cicloestrella.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "teachers")
@Getter
@Setter
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100 , nullable = false ,name = "first_name")
    private String firstName;

    @Column(length = 100 , nullable = false ,name = "last_name")
    private String lastName;

    @Column(nullable = true , name = "general_description" , length = 4000)
    private String generalDescription;

    @Column(nullable = true , name = "profile_picture_url" , length = 1000)
    private String profilePictureURL;

    @Column(
            name = "average_rating",
            precision = 2,
            scale = 2,
            nullable = true,
            columnDefinition = "DECIMAL(2,2) DEFAULT 0.00"
    )
    private BigDecimal averageRating;

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
