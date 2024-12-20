package com.lms.api.common.repository;

import com.lms.api.common.entity.MemberConsultationEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberConsultationRepository extends JpaRepository<MemberConsultationEntity, Long>,
    QuerydslPredicateExecutor<MemberConsultationEntity> {

  List<MemberConsultationEntity> findAllByUserEntity_Id(String userId);
}
