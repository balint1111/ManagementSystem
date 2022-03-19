package com.example.managementsystem.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;


/**
 * @author A Nono
 * <p>
 * Predicate is generic.
 * For example: String
 * search = Promera  -- refers to a company name
 * <p>
 * Long
 * userId = 1  -- refers to an user's id
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class GenericPageRequest<T> {
    private Pageable pageable;
    private T predicate;
}
