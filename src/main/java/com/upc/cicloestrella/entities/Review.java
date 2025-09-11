package com.upc.cicloestrella.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Review {
    @EmbeddedId
    private StudentTeacherID id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("teacherId")
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToMany
    @JoinTable(
            name = "review_reactions",
            joinColumns = {
                    @JoinColumn(name = "student_id", referencedColumnName = "student_id"),
                    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
            },
            inverseJoinColumns = @JoinColumn(name = "reaction_id")
    )
    private List<Reaction> reactions;

    @ManyToMany
    @JoinTable(
            name = "review_tags",
            joinColumns = {
                    @JoinColumn(name = "student_id", referencedColumnName = "student_id"),
                    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id")
            },
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tags> tags;

    private String description;

    @Column(precision = 2, scale = 2, nullable = false)
    private BigDecimal rating;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;


}
