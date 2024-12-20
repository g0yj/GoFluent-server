package com.lms.api.common.repository;

import com.lms.api.common.code.UserType;
import com.lms.api.common.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<UserEntity, String>,
    QuerydslPredicateExecutor<UserEntity> {

  List<UserEntity> findAllByIdIn(List<String> ids);

  List<UserEntity> findAllByTypeAndActiveTrue(UserType type);

  Optional<UserEntity> findByLoginId(String loginId);

  Optional<UserEntity> findFirstByNameAndType(String name, UserType type);

  boolean existsByLoginId(String email);

  @Query(value = "SELECT id FROM user_ ORDER BY created_on DESC LIMIT 1", nativeQuery = true)
  String findLatestUserId();

  Optional<UserEntity> findByCellPhone(String cellPhone);

  UserEntity findByNameAndTypeNot(String name, UserType type);

}
