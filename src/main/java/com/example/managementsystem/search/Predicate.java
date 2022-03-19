package com.example.managementsystem.search;


import com.querydsl.core.types.dsl.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Predicate {
    private SearchCriteria criteria;

    public Predicate(final SearchCriteria criteria) {
        this.criteria = criteria;
    }

    public BooleanExpression getPredicate(PathBuilder<?> entityPath) {
        String stringValue = criteria.getValue().toString();
//        System.out.println("criteria:  " + criteria.getKey() + " " + criteria.getOperation() + " " + criteria.getValue()); // TODO debug
        if (isNumeric(stringValue)) {
            final NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
            final int value = Integer.parseInt(criteria.getValue().toString());
            switch (criteria.getOperation()) {
                case ":":
                    return path.eq(value);
                case "=":
                    return path.eq(value);
                case ">":
                    return path.goe(value);
                case "<":
                    return path.loe(value);
                default:
                    return path.isNull();
            }
        } else {
            if (stringValue.startsWith("$")) {
                stringValue = stringValue.replace("$", ""); // eszköz hozzárendelés kezelésére hogy ha szám jön be de stringként kell keresni.
            }
            if (isBoolean(stringValue)) {
                final BooleanPath booleanPath = entityPath.getBoolean(criteria.getKey());
                Boolean value = "true".equalsIgnoreCase(stringValue) ? Boolean.TRUE : Boolean.FALSE;
                return booleanPath.eq(value);
            }
            if (isDate(stringValue)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(criteria.getValue().toString(), formatter);
                final DatePath path = entityPath.getDate(criteria.getKey(), LocalDate.class);
                if (criteria.getOperation().equals("<=")) {
                    return path.before(date);
                }
                if (criteria.getOperation().equals(">=")) {
                    return path.after(date);
                }
                if (criteria.getOperation().equals("=")) {
                    return path.eq(date);
                }
            }
            final StringPath path = entityPath.getString(criteria.getKey());
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                return path.contains(stringValue);
            }
            if (criteria.getOperation().equalsIgnoreCase("=")) {
                return path.contains(stringValue);
            }
            if (criteria.getOperation().equalsIgnoreCase("!=")) {
                return path.notEqualsIgnoreCase(stringValue);
            }
        }
        return null;
    }

    public static boolean isNumeric(final String str) {
        try {
            if (!str.startsWith("+")) {
                Integer.parseInt(str);
            } else {
                return false;
            }
        } catch (final NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isBoolean(final String str) {
        if ("false".equalsIgnoreCase(str) || "true".equalsIgnoreCase(str)) {
            return true;
        }
        return false;
    }

    public static boolean isDate(final String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate date = LocalDate.parse(str, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
