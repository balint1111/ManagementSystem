package com.example.managementsystem.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "tool")
@Data
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "identifier", nullable = false)
    private String identifier;

    @Column(name = "description")
    private String description;

    //TODO: elhelyezkedés


}
