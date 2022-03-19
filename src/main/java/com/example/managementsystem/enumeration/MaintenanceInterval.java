package com.example.managementsystem.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MaintenanceInterval {
    WEEK,
    MONTH,
    QUARTER,
    HALF_YEAR,
    YEAR;

    private String category;
}