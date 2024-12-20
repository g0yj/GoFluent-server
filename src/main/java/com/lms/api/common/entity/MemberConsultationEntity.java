package com.lms.api.common.entity;

import com.lms.api.common.code.YN;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "member_consultation")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberConsultationEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  LocalDateTime consultationDate;
  String type;
  String details;

  @Enumerated(EnumType.STRING)
  YN topFixedYn;
  @Enumerated(EnumType.STRING)
  YN fontBoldYn;

  String backgroundColor;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", nullable = false)
  UserEntity userEntity;
}
