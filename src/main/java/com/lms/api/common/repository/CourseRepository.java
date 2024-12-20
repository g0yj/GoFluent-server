package com.lms.api.common.repository;

import com.lms.api.common.entity.CourseEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CourseRepository extends JpaRepository<CourseEntity, Long>, QuerydslPredicateExecutor<CourseEntity> {
  List<CourseEntity> findAllByUserEntity_Id(String userId);
}
