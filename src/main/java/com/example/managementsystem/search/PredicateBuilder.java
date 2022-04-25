package com.example.managementsystem.search;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PredicateBuilder {
    private final List<SearchCriteria> params = new ArrayList<>();

    private static final String PATTERN = "(\\((AND|OR)\\))?([.\\w]+?)(!?[:<>=]+)([-:\\w\\u00C0-\\u00FF\\s.$@+]+?);";

    public PredicateBuilder(String searchString) {
        super();

        if (searchString != null) {
            Pattern pattern = Pattern.compile(PATTERN);
            Matcher matcher = pattern.matcher(searchString + ";");
            while (matcher.find()) {
                with(matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5));
            }
        }

    }

    public PredicateBuilder with(
            String andOr, String key, String operation, Object value) {

        params.add(new SearchCriteria(andOr, operation, key, value));
        return this;
    }

    public BooleanExpression build(PathBuilder<?> entityPath) {
        if (params.isEmpty()) {
            return null;
        }
        BooleanExpression result = Expressions.asBoolean(true).isTrue();
        for (SearchCriteria param : params) {
            BooleanExpression predicate = new Predicate(param).getPredicate(entityPath);
            if (param.getAndOr() == null || param.getAndOr().equals("AND")) {
                result = result.and(predicate);
            } else if (param.getAndOr().equals("OR")) {
                result = result.or(predicate);
            } else {
                result = result.and(predicate);
            }
        }
        return result;
    }
}

