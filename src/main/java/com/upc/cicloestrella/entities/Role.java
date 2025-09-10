package com.upc.cicloestrella.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = true)
    public String RoleName;

    @ManyToMany(mappedBy = "roles")
    public List<User> users;

}
