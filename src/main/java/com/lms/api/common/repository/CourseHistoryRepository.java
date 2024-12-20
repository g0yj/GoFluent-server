package com.lms.api.common.repository;

import com.lms.api.common.entity.CourseHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CourseHistoryRepository extends JpaRepository<CourseHistoryEntity, Long>,
    QuerydslPredicateExecutor<CourseHistoryEntity> {

}
