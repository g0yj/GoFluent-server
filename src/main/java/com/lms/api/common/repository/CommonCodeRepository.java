package com.lms.api.common.repository;

import com.lms.api.common.entity.CodePK;
import com.lms.api.common.entity.CommonCodeEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CommonCodeRepository extends JpaRepository<CommonCodeEntity, CodePK>,
    QuerydslPredicateExecutor<CommonCodeEntity> {

  List<CommonCodeEntity> findAllByCodeGroup(String codeGroup);

  Optional<CommonCodeEntity> findByCode(String code);

  List<CommonCodeEntity> findByCodeGroupAndUseYnOrderBySortAsc(String codeGroupId, String y);
}
