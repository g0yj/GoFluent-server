package com.lms.api.common.repository;

import com.lms.api.common.entity.RefundEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RefundRepository extends JpaRepository<RefundEntity, String>,
    QuerydslPredicateExecutor<RefundEntity> {

}
