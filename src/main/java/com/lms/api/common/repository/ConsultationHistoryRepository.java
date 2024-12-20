package com.lms.api.common.repository;

import com.lms.api.common.entity.ConsultationHistoryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ConsultationHistoryRepository extends
    JpaRepository<ConsultationHistoryEntity, Long>,
    QuerydslPredicateExecutor<ConsultationHistoryEntity> {

  List<ConsultationHistoryEntity> findByConsultationEntity_Id(Long consultationId);
}
