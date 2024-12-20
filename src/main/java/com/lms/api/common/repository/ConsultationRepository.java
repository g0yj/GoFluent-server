package com.lms.api.common.repository;

import com.lms.api.common.entity.ConsultationEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ConsultationRepository extends JpaRepository<ConsultationEntity, Long>,
    QuerydslPredicateExecutor<ConsultationEntity> {

  Optional<ConsultationEntity> findByCellPhone(String cellPhone);

  boolean existsByCellPhone(String cellphone);

  @Query("SELECT MAX(e.id) FROM ConsultationEntity e")
  Long findMaxId();

}
