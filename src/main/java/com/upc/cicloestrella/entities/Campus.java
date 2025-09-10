package com.upc.cicloestrella.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "campuses")
public class Campus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy ="campuses")
    private List<Teacher> teachers;

}
