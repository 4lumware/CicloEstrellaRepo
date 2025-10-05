package com.upc.cicloestrella.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(unique = true, nullable = false ,name = "format_name")
    private String formatName;
    @ManyToMany(mappedBy = "formats")
    @JsonIgnore
    private List<Course> courses;
}
