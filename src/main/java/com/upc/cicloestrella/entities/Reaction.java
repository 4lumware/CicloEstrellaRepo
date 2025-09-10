package com.upc.cicloestrella.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Reaction{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(nullable = true)
    private String icon_url;

    @ManyToMany(mappedBy = "reactions")
    private List<Review> reviews;
}
