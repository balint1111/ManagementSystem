package com.example.managementsystem.services;

import com.example.managementsystem.entities.RelEducationUser;
import com.example.managementsystem.repositoies.RelEducationUserRepository;
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
public class RelEducationUserService {
    private final RelEducationUserRepository repository;

    public RelEducationUserService(RelEducationUserRepository repository) {
        this.repository = repository;
    }

    public RelEducationUser save(RelEducationUser entity) {
        return repository.save(entity);
    }

    public List<RelEducationUser> saveAll(List<RelEducationUser> entities) {
        return repository.saveAll(entities);
    }

    public RelEducationUser getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<RelEducationUser> listAll() {
        return repository.findAll();
    }

    public List<RelEducationUser> listAllByEducationId(Long educationId) {
        return repository.findAllByEducation_Id(educationId);
    }

    public List<RelEducationUser> listAllByUserId(Long userId) {
        return repository.findAllByUser_Id(userId);
    }

    public List<RelEducationUser> getAll(String predicateStr) {
        List<RelEducationUser> list;
        try {
            if (StringUtils.isEmpty(predicateStr)) {
                return repository.findAll();
            }
            PathBuilder<RelEducationUser> pathBuilder = new PathBuilder<>(RelEducationUser.class, "relEducationUser");
            BooleanExpression predicate = new PredicateBuilder(predicateStr).build(pathBuilder);
            list = Lists.newArrayList(repository.findAll(predicate));
        } catch (EntityNotFoundException e) {
            return Collections.emptyList();
        }
        return list;
    }

    public RestResponsePage<RelEducationUser> getAllPageable(GetAllPageableRequest request) {
        Page<RelEducationUser> page;
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
            PathBuilder<RelEducationUser> pathBuilder = new PathBuilder<>(RelEducationUser.class, "relEducationUser");
            BooleanExpression predicate = new PredicateBuilder(request.getPredicate()).build(pathBuilder);
            page = repository.findAll(predicate, pageable);
        } catch (EntityNotFoundException e) {
            return new RestResponsePage<>(Collections.emptyList());
        }
        return convertToPageEntry(page);
    }

    private RestResponsePage<RelEducationUser> convertToPageEntry(Page<RelEducationUser> page) {
        if (page != null && page.hasContent()) {
            return new RestResponsePage<>(page.getContent(), page.getPageable(), page.getTotalElements());
        } else {
            return new RestResponsePage<>(Collections.emptyList());
        }
    }
}
