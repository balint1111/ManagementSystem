package com.example.managementsystem.entities;

import com.example.managementsystem.enumeration.MaintenanceInterval;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity(name = "tool_category")
@Data
public class ToolCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "category", nullable = false)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(name = "maintenance_interval")
    private MaintenanceInterval maintenanceInterval;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_category_id", referencedColumnName = "id")
    private ToolCategory parentCategory;

    public MaintenanceInterval getMaintenanceIntervalPro() throws Exception {
        if(maintenanceInterval != null){
            return maintenanceInterval;
        } else if (parentCategory != null) {
            return parentCategory.getMaintenanceIntervalPro();
        } else {
            throw new Exception("NO_MAINTENANCE_INTERVAL");
        }
    }
}
