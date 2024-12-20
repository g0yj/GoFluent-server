package com.lms.api.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "refund")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefundEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  String id;

  LocalDate refundDate;
  int refundAmount;
  int cashAmount;
  int depositAmount;
  String bank;
  String accountNumber;
  int cardAmount;
  String refundReason;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", nullable = false)
  UserEntity userEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "orderProductId", nullable = false)
  OrderProductEntity orderProductEntity;
}
