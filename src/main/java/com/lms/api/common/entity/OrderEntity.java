package com.lms.api.common.entity;

import com.lms.api.common.code.OrderType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "order_")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  String id;

  @Enumerated(EnumType.STRING)
  OrderType type;

  int supplyAmount;
  int discountAmount;
  int billingAmount;
  int cashAmount;
  int depositAmount;
  int cardCount;
  int cardAmount;
  int paymentAmount;
  int receivableAmount;
  int refundAmount;
  LocalDate recallDate;
  String receivableReason;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", nullable = false)
  UserEntity userEntity;

  @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<OrderProductEntity> orderProductEntities = new ArrayList<>();

  @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<PaymentEntity> paymentEntities = new ArrayList<>();
}
