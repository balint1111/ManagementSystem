package com.example.managementsystem.repositoies;

import com.example.managementsystem.entities.Tool;
import com.example.managementsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ToolRepository extends JpaRepository<Tool, Long>, QuerydslPredicateExecutor<Tool> {
}