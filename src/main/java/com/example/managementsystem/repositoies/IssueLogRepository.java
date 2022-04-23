package com.example.managementsystem.repositoies;

import com.example.managementsystem.entities.IssueLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface IssueLogRepository extends JpaRepository<IssueLog, Long>, QuerydslPredicateExecutor<IssueLog> {
}