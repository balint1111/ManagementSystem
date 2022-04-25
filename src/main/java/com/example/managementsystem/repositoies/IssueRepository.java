package com.example.managementsystem.repositoies;

import com.example.managementsystem.entities.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, Long>, QuerydslPredicateExecutor<Issue> {
    Optional<Issue> findFirstByTool_IdOrderByDateTimeDesc(Long toolId);
}