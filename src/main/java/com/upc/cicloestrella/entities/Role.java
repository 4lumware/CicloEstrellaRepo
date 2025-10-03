package com.upc.cicloestrella.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    public enum RoleName {
        ADMIN,
        STUDENT,
        MODERATOR,
        WRITER
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, unique = true, nullable = false , name = "role_name")
    private RoleName roleName;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private List<User> users;

}
