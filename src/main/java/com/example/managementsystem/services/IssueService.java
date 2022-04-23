package com.example.managementsystem.services;

import com.example.managementsystem.entities.Issue;
import com.example.managementsystem.entities.IssueLog;
import com.example.managementsystem.enumeration.IssueStatus;
import com.example.managementsystem.repositoies.IssueLogRepository;
import com.example.managementsystem.repositoies.IssueRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

@Service
public class IssueService {
    private final IssueRepository repository;

    private final IssueLogRepository issueLogRepository;

    public IssueService(IssueRepository repository, IssueLogRepository issueLogRepository) {
        this.repository = repository;
        this.issueLogRepository = issueLogRepository;
    }

    @Transactional
    public Issue save(Issue entity) {
        Issue oldIssue = null;
        IssueStatus oldStatus = null;
        Long oldResponsibleUserId = null;
        if (entity.getId() != null) {
            oldIssue = repository.findById(entity.getId()).get();
            if (oldIssue != null) {
                oldStatus = oldIssue.getStatus();
                oldResponsibleUserId = oldIssue.getResponsibleUser() != null ? oldIssue.getResponsibleUser().getId() : null;
            }
        }
        Issue newIssue = repository.save(entity);
        issueLogging(newIssue, oldStatus, oldResponsibleUserId);

        return newIssue;
    }

    private void issueLogging(Issue newIssue, IssueStatus oldStatus, Long oldResponsibleUserId){
        if (oldStatus != null && oldStatus.toString() != newIssue.getStatus().toString()) {
            issueLogRepository.save(new IssueLog(null, newIssue.getId(), "Status changed to " + newIssue.getStatus().toString()));
        }
        if (oldResponsibleUserId != null && !oldResponsibleUserId.equals(newIssue.getResponsibleUser().getId())) {
            issueLogRepository.save(new IssueLog(null, newIssue.getId(), "Responsible user changed to " + newIssue.getResponsibleUser().getUsername()));
        }
    }

    public List<Issue> saveAll(List<Issue> entities) {
        return repository.saveAll(entities);
    }



    public Issue getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Issue> listAll() {
        return repository.findAll();
    }

    public List<Issue> getAll(String predicateStr) {
        List<Issue> list;
        try {
            if (StringUtils.isEmpty(predicateStr)) {
                return repository.findAll();
            }
            PathBuilder<Issue> pathBuilder = new PathBuilder<>(Issue.class, "issue");
            BooleanExpression predicate = new PredicateBuilder(predicateStr).build(pathBuilder);
            list = Lists.newArrayList(repository.findAll(predicate));
        } catch (EntityNotFoundException e) {
            return Collections.emptyList();
        }
        return list;
    }

    public RestResponsePage<Issue> getAllPageable(GetAllPageableRequest request) {
        Page<Issue> page;
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
            PathBuilder<Issue> pathBuilder = new PathBuilder<>(Issue.class, "issue");
            BooleanExpression predicate = new PredicateBuilder(request.getPredicate()).build(pathBuilder);
            page = repository.findAll(predicate, pageable);
        } catch (EntityNotFoundException e) {
            return new RestResponsePage<>(Collections.emptyList());
        }
        return convertToPageEntry(page);
    }

    private RestResponsePage<Issue> convertToPageEntry(Page<Issue> page) {
        if (page != null && page.hasContent()) {
            return new RestResponsePage<>(page.getContent(), page.getPageable(), page.getTotalElements());
        } else {
            return new RestResponsePage<>(Collections.emptyList());
        }
    }
}
