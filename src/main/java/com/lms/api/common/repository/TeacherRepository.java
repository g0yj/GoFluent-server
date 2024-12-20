package com.lms.api.common.repository;

import com.lms.api.common.entity.TeacherEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TeacherRepository extends JpaRepository<TeacherEntity, String>,
    QuerydslPredicateExecutor<TeacherEntity> {

  List<TeacherEntity> findAllByActiveTrueAndUserEntityIsNotNullOrderBySort();

  Optional<TeacherEntity> findByUserId(String updateTeacher);
}
