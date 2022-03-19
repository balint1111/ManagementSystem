package com.example.managementsystem.repositoies;

import com.example.managementsystem.entities.RelEducationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface RelEducationUserRepository extends JpaRepository<RelEducationUser, Long>, QuerydslPredicateExecutor<RelEducationUser> {
    List<RelEducationUser> findAllByEducation_Id(Long education_id);

    List<RelEducationUser> findAllByUser_Id(Long userId);
}