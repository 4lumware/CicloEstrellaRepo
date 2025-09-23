package com.upc.cicloestrella.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "reactions")
public class Reaction{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false ,name = "reaction_name", length = 100)
    private String reactionName;

    @Column(nullable = true , length = 1000)
    private String icon_url;

    @OneToMany(mappedBy = "reaction" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewReaction> reviewReactions;
}
