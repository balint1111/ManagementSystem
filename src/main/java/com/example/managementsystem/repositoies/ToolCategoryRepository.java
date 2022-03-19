package com.example.managementsystem.repositoies;

import com.example.managementsystem.entities.ToolCategory;
import com.example.managementsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface ToolCategoryRepository extends JpaRepository<ToolCategory, Long>, QuerydslPredicateExecutor<ToolCategory> {
}