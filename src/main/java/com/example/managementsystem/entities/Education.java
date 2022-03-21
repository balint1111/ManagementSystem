package com.example.managementsystem.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "education")
@Data
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
