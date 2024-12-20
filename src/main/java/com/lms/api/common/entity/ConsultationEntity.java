package com.lms.api.common.entity;

import com.lms.api.common.code.Gender;
import com.lms.api.common.code.LevelTestType;
import com.lms.api.common.code.YN;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "consultation")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConsultationEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String institutionId = "C1352081511487906";
  LocalDateTime consultationDate;
  String name;

  @Enumerated(EnumType.STRING)
  Gender gender;


  String job;
  String company;
  String phone;
  String cellPhone;
  String foundPath;
  String foundPathNote;
  LocalDateTime visitDate;
  String details;

  String file;
  String originalFile;

  @Enumerated(EnumType.STRING)
  YN isMember;

/*
  공통코드 사용으로 수정
  @Enumerated(EnumType.STRING)
  ConsultationType type;
  */
  String type;

  String studyPurpose;
  String etcStudyPurpose;
  String callTime;
  String email;
  String status;

  @Enumerated(EnumType.STRING)
  LevelTestType levelTestType;

  String levelTestAnswer;
  Integer levelTestCorrectCount;

  @OneToMany(mappedBy = "consultationEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<ConsultationHistoryEntity> consultationHistoryEntities = new ArrayList<>();
}
