package com.example.managementsystem.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum IssueSeverity {
    LOW,
    NORMAL,
    HIGH;

    private String name;
}