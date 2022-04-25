package com.example.managementsystem.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericSingleResponse<T> extends BaseRestResponse {
    private T item;

    public GenericSingleResponse(String status, String cause, T item) {
        super(status, cause);
        this.item = item;
    }
}
