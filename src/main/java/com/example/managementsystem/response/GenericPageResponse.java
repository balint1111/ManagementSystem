package com.example.managementsystem.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GenericPageResponse<T> extends BaseRestResponse {
    private Page<T> page;

    public GenericPageResponse(String status, String cause, Page<T> page) {
        super(status, cause);
        this.page = page;
    }
}
