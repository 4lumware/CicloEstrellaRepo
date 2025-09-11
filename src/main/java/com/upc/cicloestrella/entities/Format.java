package com.upc.cicloestrella.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "formats")
@Getter
@Setter
public class Format {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String formatName;
    @ManyToMany(mappedBy = "formats")
    private List<Course> courses;
}
