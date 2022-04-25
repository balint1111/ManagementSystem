package com.example.managementsystem.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericListResponse<T> extends BaseRestResponse {
    private List<T> items;

    public GenericListResponse(String status, String cause, List<T> items) {
        super(status, cause);
        this.items = items;
    }
}
