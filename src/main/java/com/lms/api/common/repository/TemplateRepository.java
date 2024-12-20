package com.lms.api.common.repository;

import com.lms.api.common.entity.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TemplateRepository extends JpaRepository<TemplateEntity, Long>,
    QuerydslPredicateExecutor<TemplateEntity> {

}
