package com.lms.api.common.repository;

import com.lms.api.common.entity.LevelTestEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface LevelTestRepository extends JpaRepository<LevelTestEntity, Long>,
    QuerydslPredicateExecutor<LevelTestEntity> {

  List<LevelTestEntity> findAllByUserEntity_Id(String userId);

}
