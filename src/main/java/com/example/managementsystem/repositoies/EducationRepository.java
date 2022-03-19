package com.example.managementsystem.repositoies;

import com.example.managementsystem.entities.Education;
import com.example.managementsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface EducationRepository extends JpaRepository<Education, Long>, QuerydslPredicateExecutor<Education> {
}