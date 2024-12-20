package com.lms.api.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "consultation_history")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConsultationHistoryEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  LocalDateTime date;
  String details;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "consultationId")
  ConsultationEntity consultationEntity;
}
