package com.lms.api.common.repository;

import com.lms.api.common.entity.EmailEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface EmailRepository
    extends JpaRepository<EmailEntity, Long>, QuerydslPredicateExecutor<EmailEntity> {

    boolean existsByLdfId(Long ldfId);

    List<EmailEntity> findAllByLdfId(Long ldfId);

}
