package com.example.managementsystem.services;

import com.example.managementsystem.entities.RelEducationToolCategory;
import com.example.managementsystem.entities.ToolCategory;
import com.example.managementsystem.repositoies.ToolCategoryRepository;
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
import java.util.stream.Collectors;

@Service
public class ToolCategoryService {
    private final ToolCategoryRepository repository;
    private final RelEducationToolCategoryService relEducationToolCategoryService;

    public ToolCategoryService(ToolCategoryRepository repository, RelEducationToolCategoryService relEducationToolCategoryService) {
        this.repository = repository;
        this.relEducationToolCategoryService = relEducationToolCategoryService;
    }

    public ToolCategory save(ToolCategory entity) throws Exception {
        validate(entity);
        return repository.save(entity);
    }

    public List<ToolCategory> saveAll(List<ToolCategory> entities) throws Exception {
        for (ToolCategory toolCategory : entities) {
            validate(toolCategory);
        }
        return repository.saveAll(entities);
    }

    private boolean validate(ToolCategory entity) throws Exception {
        if (entity.getMaintenanceInterval() == null) {
            ToolCategory parent = null;
            if (entity.getParentCategory() != null && entity.getParentCategory().getId() != null) {
                parent = getById(entity.getParentCategory().getId());
            }
            if (parent == null || parent.getId() == null || parent.getMaintenanceIntervalPro() == null) {
                throw new Exception("NO_MAINTENANCE_INTERVAL");
            }
        }
        if (entity.getParentCategory() != null && entity.getParentCategory().getId() == null) {
            entity.setParentCategory(null);
        }
        return true;
    }

    public ToolCategory getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<ToolCategory> listAll() {
        return repository.findAll();
    }

    public List<ToolCategory> listAllByEducation(Long educationId) {
        return relEducationToolCategoryService.listAllByEducationId(educationId).stream().map(RelEducationToolCategory::getToolCategory).collect(Collectors.toList());
    }

    public List<ToolCategory> getAll(String predicateStr) throws Exception {
        List<ToolCategory> list;
        try {
            if (StringUtils.isEmpty(predicateStr)) {
                return repository.findAll();
            }
            PathBuilder<ToolCategory> pathBuilder = new PathBuilder<>(ToolCategory.class, "toolCategory");
            BooleanExpression predicate = new PredicateBuilder(predicateStr).build(pathBuilder);
            list = Lists.newArrayList(repository.findAll(predicate));
            for (ToolCategory toolCategory : list) {
                toolCategory.setMaintenanceInterval(toolCategory.getMaintenanceIntervalPro());
            }
        } catch (EntityNotFoundException e) {
            return Collections.emptyList();
        }
        return list;
    }

    public RestResponsePage<ToolCategory> getAllPageable(GetAllPageableRequest request) throws Exception {
        Page<ToolCategory> page;
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
            PathBuilder<ToolCategory> pathBuilder = new PathBuilder<>(ToolCategory.class, "toolCategory");
            BooleanExpression predicate = new PredicateBuilder(request.getPredicate()).build(pathBuilder);
            page = repository.findAll(predicate, pageable);
        } catch (EntityNotFoundException e) {
            return new RestResponsePage<>(Collections.emptyList());
        }
        return convertToPageEntry(page);
    }

    private RestResponsePage<ToolCategory> convertToPageEntry(Page<ToolCategory> page) throws Exception {
        if (page != null && page.hasContent()) {
            List<ToolCategory> list = page.getContent();
            for (ToolCategory toolCategory : list) {
                toolCategory.setMaintenanceInterval(toolCategory.getMaintenanceIntervalPro());
            }
            return new RestResponsePage<>(list, page.getPageable(), page.getTotalElements());
        } else {
            return new RestResponsePage<>(Collections.emptyList());
        }
    }
}
