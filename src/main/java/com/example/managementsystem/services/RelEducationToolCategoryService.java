package com.example.managementsystem.services;

import com.example.managementsystem.entities.RelEducationToolCategory;
import com.example.managementsystem.repositoies.RelEducationToolCategoryRepository;
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
public class RelEducationToolCategoryService {
    private final RelEducationToolCategoryRepository repository;

    public RelEducationToolCategoryService(RelEducationToolCategoryRepository repository) {
        this.repository = repository;
    }

    public RelEducationToolCategory save(RelEducationToolCategory entity) {
        return repository.save(entity);
    }

    public List<RelEducationToolCategory> saveAll(List<RelEducationToolCategory> entities) {
        return repository.saveAll(entities);
    }

    public RelEducationToolCategory getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<RelEducationToolCategory> listAll() {
        return repository.findAll();
    }

    public List<RelEducationToolCategory> listAllByEducationId(Long educationId) {
        return repository.findAllByEducation_Id(educationId);
    }

    public List<RelEducationToolCategory> listAllByToolCategoryId(Long toolCategoryId) {
        return repository.findAllByToolCategory_Id(toolCategoryId);
    }

    public List<RelEducationToolCategory> getAll(String predicateStr) {
        List<RelEducationToolCategory> list;
        try {
            if (StringUtils.isEmpty(predicateStr)) {
                return repository.findAll();
            }
            PathBuilder<RelEducationToolCategory> pathBuilder = new PathBuilder<>(RelEducationToolCategory.class, "relEducationToolCategory");
            BooleanExpression predicate = new PredicateBuilder(predicateStr).build(pathBuilder);
            list = Lists.newArrayList(repository.findAll(predicate));
        } catch (EntityNotFoundException e) {
            return Collections.emptyList();
        }
        return list;
    }

    public RestResponsePage<RelEducationToolCategory> getAllPageable(GetAllPageableRequest request) {
        Page<RelEducationToolCategory> page;
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
            PathBuilder<RelEducationToolCategory> pathBuilder = new PathBuilder<>(RelEducationToolCategory.class, "relEducationToolCategory");
            BooleanExpression predicate = new PredicateBuilder(request.getPredicate()).build(pathBuilder);
            page = repository.findAll(predicate, pageable);
        } catch (EntityNotFoundException e) {
            return new RestResponsePage<>(Collections.emptyList());
        }
        return convertToPageEntry(page);
    }

    private RestResponsePage<RelEducationToolCategory> convertToPageEntry(Page<RelEducationToolCategory> page) {
        if (page != null && page.hasContent()) {
            return new RestResponsePage<>(page.getContent(), page.getPageable(), page.getTotalElements());
        } else {
            return new RestResponsePage<>(Collections.emptyList());
        }
    }
}
