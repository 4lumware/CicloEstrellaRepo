package com.upc.cicloestrella.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100 , nullable = false ,name = "first_name")
    private String firstName;

    @Column(length = 100 , nullable = false ,name = "last_name")
    private String lastName;

    @Column(name = "general_description" , length = 4000)
    private String generalDescription;

    @Column(name = "profile_picture_url" , length = 1000)
    private String profilePictureURL;

    @Column(
            name = "average_rating",
            precision = 3,
            scale = 2,
            nullable = true,
            columnDefinition = "DECIMAL(3,2) DEFAULT 0.00"
    )

    private BigDecimal averageRating;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "teacher_careers",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "career_id")
    )
    @JsonManagedReference
    private List<Career> careers;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "teacher_campuses",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "campus_id")
    )
    @JsonManagedReference
    private List<Campus> campuses;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "teacher_courses",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonManagedReference
    private List<Course> courses;

    @PrePersist
    public void prePersist() {
        if (averageRating == null) {
            averageRating = BigDecimal.valueOf(0.00);
        }
    }

}
