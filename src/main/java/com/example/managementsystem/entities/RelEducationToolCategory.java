package com.example.managementsystem.entities;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "rel_education_tool_category")
@Data
public class RelEducationToolCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "education_id", nullable = false)
    private Education education;

    @ManyToOne
    @JoinColumn(name = "tool_category_id", nullable = false)
    private ToolCategory toolCategory;
}
