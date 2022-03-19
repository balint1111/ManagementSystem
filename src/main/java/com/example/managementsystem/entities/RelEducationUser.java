package com.example.managementsystem.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "rel_education_user")
@Data
public class RelEducationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "education_id", nullable = false)
    private Education education;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
