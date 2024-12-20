package com.lms.api.common.repository;

import com.lms.api.common.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductRepository extends JpaRepository<ProductEntity, String>,
    QuerydslPredicateExecutor<ProductEntity> {

}
