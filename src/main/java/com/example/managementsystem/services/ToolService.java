package com.example.managementsystem.services;

import com.example.managementsystem.entities.Tool;
import com.example.managementsystem.repositoies.ToolRepository;
import com.example.managementsystem.request.GetAllPageableRequest;
import com.example.managementsystem.search.PredicateBuilder;
import com.example.managementsystem.search.RestResponsePage;
import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

@Service
public class ToolService {
    private final ToolRepository repository;

    public Tool save (Tool entity) {
        return repository.save(entity);
    }

    public List<Tool> saveAll (List<Tool> entities) {
        return repository.saveAll(entities);
    }

    public ToolService(ToolRepository repository) {
        this.repository = repository;
    }

    public Tool getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Tool> listAll() {
        return repository.findAll();
    }

    public List<Tool> getAll(String predicateStr) {
        List<Tool> list;
        try {
            if (StringUtils.isEmpty(predicateStr)) {
                return repository.findAll();
            }
            PathBuilder<Tool> pathBuilder = new PathBuilder<>(Tool.class, "tool");
            BooleanExpression predicate = new PredicateBuilder(predicateStr).build(pathBuilder);
            list = Lists.newArrayList(repository.findAll(predicate));
        } catch (EntityNotFoundException e) {
            return Collections.emptyList();
        }
        return list;
    }

    public RestResponsePage<Tool> getAllPageable(GetAllPageableRequest request) {
        Page<Tool> page;
        Pageable pageable;
        try {
            if (request.getPageable() == null) {
                pageable = PageRequest.of(0, 10);
            } else {
                pageable = request.getPageable().toPageable();
            }
            if (StringUtils.isEmpty(request.getPredicate())) {
                return convertToPageEntry(repository.findAll(pageable));
            }
            PathBuilder<Tool> pathBuilder = new PathBuilder<>(Tool.class, "tool");
            BooleanExpression predicate = new PredicateBuilder(request.getPredicate()).build(pathBuilder);
            page = repository.findAll(predicate, pageable);
        } catch (EntityNotFoundException e) {
            return new RestResponsePage<>(Collections.emptyList());
        }
        return convertToPageEntry(page);
    }

    private RestResponsePage<Tool> convertToPageEntry(Page<Tool> page) {
        if (page != null && page.hasContent()) {
            return new RestResponsePage<>(page.getContent(), page.getPageable(), page.getTotalElements());
        } else {
            return new RestResponsePage<>(Collections.emptyList());
        }
    }
}
