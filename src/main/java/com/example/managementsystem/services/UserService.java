package com.example.managementsystem.services;

import com.example.managementsystem.entities.User;
import com.example.managementsystem.repositoies.UserRepository;
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
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User save(User entity) {
        return repository.save(entity);
    }

    public List<User> saveAll(List<User> entities) {
        return repository.saveAll(entities);
    }

    public User getByUsernameAndPassword(String username, String password) {
        return repository.findByUsernameAndPassword(username, password);
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public List<User> getAll(String predicateStr) {
        List<User> list;
        try {
            if (StringUtils.isEmpty(predicateStr)) {
                return repository.findAll();
            }
            PathBuilder<User> pathBuilder = new PathBuilder<>(User.class, "user");
            BooleanExpression predicate = new PredicateBuilder(predicateStr).build(pathBuilder);
            list = Lists.newArrayList(repository.findAll(predicate));
        } catch (EntityNotFoundException e) {
            return Collections.emptyList();
        }
        return list;
    }

    public RestResponsePage<User> getAllPageable(GetAllPageableRequest request) {
        Page<User> page;
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
            PathBuilder<User> pathBuilder = new PathBuilder<>(User.class, "user");
            BooleanExpression predicate = new PredicateBuilder(request.getPredicate()).build(pathBuilder);
            page = repository.findAll(predicate, pageable);
        } catch (EntityNotFoundException e) {
            return new RestResponsePage<>(Collections.emptyList());
        }
        return convertToPageEntry(page);
    }

    private RestResponsePage<User> convertToPageEntry(Page<User> page) {
        if (page != null && page.hasContent()) {
            return new RestResponsePage<>(page.getContent(), page.getPageable(), page.getTotalElements());
        } else {
            return new RestResponsePage<>(Collections.emptyList());
        }
    }
}
