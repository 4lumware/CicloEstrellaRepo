package com.upc.cicloestrella.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(unique = true, nullable = false ,name = "career_name")
    private String careerName;

    @ManyToMany(mappedBy = "careers")
    @JsonIgnore
    private List<Teacher> teachers;

    @ManyToMany(mappedBy = "careers")
    @JsonIgnore
    private List<Student> students;

}
