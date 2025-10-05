package com.upc.cicloestrella.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "campuses")
public class Campus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true , nullable = false, name = "campus_name")
    private String campusName;
    @ManyToMany(mappedBy ="campuses")
    @JsonIgnore
    private List<Teacher> teachers;

}
