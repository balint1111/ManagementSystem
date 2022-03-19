package com.example.managementsystem.request;

import com.example.managementsystem.search.ProPageable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllPageableRequest {
    private ProPageable pageable;
    private String predicate;
}
