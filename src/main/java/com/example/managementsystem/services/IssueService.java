package com.example.managementsystem.services;

import com.example.managementsystem.entities.Issue;
import com.example.managementsystem.entities.IssueLog;
import com.example.managementsystem.entities.User;
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
    private final AuthService authService;

    public IssueService(IssueRepository repository, IssueLogRepository issueLogRepository, AuthService authService) {
        this.repository = repository;
        this.issueLogRepository = issueLogRepository;
        this.authService = authService;
    }

    @Transactional
    public Issue save(Issue entity) {
        return save(entity, null);
    }

    @Transactional
    public Issue updateStatus(Long issueId, IssueStatus newStatus, String justification) {
        Issue oldIssue = getById(issueId);
        String oldStatus = oldIssue.getStatus().toString();
        oldIssue.setStatus(newStatus);

        Issue newIssue = repository.save(oldIssue);
        issueLogging(newIssue, oldStatus, null, justification);

        return newIssue;
    }

    @Transactional
    public Issue save(Issue entity, String justification) {
        if (entity.getResponsibleUser() != null && entity.getResponsibleUser().getId() == null) {
            entity.setResponsibleUser(null);
        }
        Issue oldIssue = null;
        String oldStatus = null;
        Long oldResponsibleUserId = null;
        if (entity.getId() != null) {
            oldIssue = getById(entity.getId());
            if (oldIssue != null) {
                oldStatus = oldIssue.getStatus().toString();
                oldResponsibleUserId = oldIssue.getResponsibleUser() != null ? oldIssue.getResponsibleUser().getId() : null;
            }
        }
        Issue newIssue = repository.save(entity);
        issueLogging(newIssue, oldStatus, oldResponsibleUserId, justification);

        return newIssue;
    }

    private void issueLogging(Issue newIssue, String oldStatus, Long oldResponsibleUserId, String justification) {
        User currentUser = authService.getCurrentUser();
        if (oldStatus == null || !oldStatus.toString().equals(newIssue.getStatus().toString())) {
            if (newIssue.getStatus() != null)
                issueLogRepository.save(new IssueLog(null, newIssue.getId(), "Status changed to " + newIssue.getStatus().toString() +
                        (currentUser != null ? " by user: " + currentUser.getUsername() : ""), justification, currentUser));
        }
        if (oldResponsibleUserId == null || (oldResponsibleUserId != null && !oldResponsibleUserId.equals(newIssue.getResponsibleUser().getId()))) {
            if (newIssue.getResponsibleUser() != null && newIssue.getResponsibleUser().getUsername() != null)
                issueLogRepository.save(new IssueLog(null, newIssue.getId(), "Responsible user changed to " + newIssue.getResponsibleUser().getUsername() +
                        (currentUser != null ? " by user: " + currentUser.getUsername() : ""), justification, currentUser));
        }
    }

    public List<Issue> saveAll(List<Issue> entities) {
        return repository.saveAll(entities);
    }


    public Issue getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Issue getLastByTool(Long toolId) {
        return repository.findFirstByTool_IdOrderByDateTimeDesc(toolId).orElse(null);
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
