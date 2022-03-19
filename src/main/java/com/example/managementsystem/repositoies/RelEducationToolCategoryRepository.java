package com.example.managementsystem.repositoies;

import com.example.managementsystem.entities.RelEducationToolCategory;
import com.example.managementsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface RelEducationToolCategoryRepository extends JpaRepository<RelEducationToolCategory, Long>, QuerydslPredicateExecutor<RelEducationToolCategory> {
    List<RelEducationToolCategory> findAllByEducation_Id(Long education_id);
    List<RelEducationToolCategory> findAllByToolCategory_Id(Long toolCategory_id);
}