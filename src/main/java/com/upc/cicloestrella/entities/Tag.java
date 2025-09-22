package com.upc.cicloestrella.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false ,name = "tag_name")
    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private List<Review> reviews;
}


