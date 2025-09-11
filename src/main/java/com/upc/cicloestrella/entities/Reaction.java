package com.upc.cicloestrella.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
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
