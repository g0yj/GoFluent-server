package com.lms.api.common.repository;

import com.lms.api.common.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OrderProductRepository extends JpaRepository<OrderProductEntity, String>, QuerydslPredicateExecutor<OrderProductEntity> {
}
