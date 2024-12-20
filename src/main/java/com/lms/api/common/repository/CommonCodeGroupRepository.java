package com.lms.api.common.repository;

import com.lms.api.common.entity.CommonCodeGroupEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CommonCodeGroupRepository extends JpaRepository<CommonCodeGroupEntity, String>,
    QuerydslPredicateExecutor<CommonCodeGroupEntity> {

  List<CommonCodeGroupEntity> findAll();

}
