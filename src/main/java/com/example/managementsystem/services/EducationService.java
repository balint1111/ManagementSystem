package com.example.managementsystem.services;

import com.example.managementsystem.entities.Education;
import com.example.managementsystem.entities.RelEducationToolCategory;
import com.example.managementsystem.entities.RelEducationUser;
import com.example.managementsystem.repositoies.EducationRepository;
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
public class EducationService {
    private final EducationRepository repository;
    private final RelEducationToolCategoryService relEducationToolCategoryService;
    private final RelEducationUserService relEducationUserService;

    public EducationService(EducationRepository repository, RelEducationToolCategoryService relEducationToolCategoryService, RelEducationUserService relEducationUserService) {
        this.repository = repository;
        this.relEducationToolCategoryService = relEducationToolCategoryService;
        this.relEducationUserService = relEducationUserService;
    }

    public Education save(Education entity) {
        return repository.save(entity);
    }

    public List<Education> saveAll(List<Education> entities) {
        return repository.saveAll(entities);
    }

    public Education getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Education> listAll() {
        return repository.findAll();
    }

    public List<Education> listAllByToolCategory(Long toolCategoryId) {
        return relEducationToolCategoryService.listAllByEducationId(toolCategoryId).stream().map(RelEducationToolCategory::getEducation).collect(Collectors.toList());
    }

    public List<Education> listAllByUserId(Long userId) {
        return relEducationUserService.listAllByUserId(userId).stream().map(RelEducationUser::getEducation).collect(Collectors.toList());
    }

    public List<Education> getAll(String predicateStr) {
        List<Education> list;
        try {
            if (StringUtils.isEmpty(predicateStr)) {
                return repository.findAll();
            }
            PathBuilder<Education> pathBuilder = new PathBuilder<>(Education.class, "education");
            BooleanExpression predicate = new PredicateBuilder(predicateStr).build(pathBuilder);
            list = Lists.newArrayList(repository.findAll(predicate));
        } catch (EntityNotFoundException e) {
            return Collections.emptyList();
        }
        return list;
    }

    public RestResponsePage<Education> getAllPageable(GetAllPageableRequest request) {
        Page<Education> page;
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
            PathBuilder<Education> pathBuilder = new PathBuilder<>(Education.class, "education");
            BooleanExpression predicate = new PredicateBuilder(request.getPredicate()).build(pathBuilder);
            page = repository.findAll(predicate, pageable);
        } catch (EntityNotFoundException e) {
            return new RestResponsePage<>(Collections.emptyList());
        }
        return convertToPageEntry(page);
    }

    private RestResponsePage<Education> convertToPageEntry(Page<Education> page) {
        if (page != null && page.hasContent()) {
            return new RestResponsePage<>(page.getContent(), page.getPageable(), page.getTotalElements());
        } else {
            return new RestResponsePage<>(Collections.emptyList());
        }
    }
}
