package com.example.managementsystem.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String andOr;
    private String operation;
    private String key;
    private Object value;
}
