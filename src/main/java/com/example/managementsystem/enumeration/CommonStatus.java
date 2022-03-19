package com.example.managementsystem.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CommonStatus {
    OK("SIKERES MŰVELET"),
    BAD_CREDENTIALS("ROSSZ EMAIL VAGY JELSZO"),
    INVALID_INPUT("HELYTELEN ADAT"),
    UNAUTHORIZED("HOZZÁFÉRÉS MEGTAGADVA"),
    CONFLICT("ÖSSZEFÉRHETETLENSÉG"),
    FORBIDDEN("NEM ADMINISZTRATOR"),
    NOT_FOUND("NEM TALÁLHATÓ"),
    NO_EMPLOYEE_FOR_USER("NEM TALÁLHATÓ DOLGOZÓ"),
    DEPENDENT("FÜGGŐSÉGI HIBA"),
    ERROR("HIBA TÖRTÉNT");

    private String statusString;
}