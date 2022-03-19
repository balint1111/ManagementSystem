package com.example.managementsystem.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum UserType {
    ADMIN,
    TOOL_MANAGER,
    OPERATOR,
    REPAIRMAN;

    private String category;
}