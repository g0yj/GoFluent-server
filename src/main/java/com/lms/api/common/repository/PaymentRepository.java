package com.lms.api.common.repository;

import com.lms.api.common.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PaymentRepository extends JpaRepository<PaymentEntity, String>,
    QuerydslPredicateExecutor<PaymentEntity> {

}
