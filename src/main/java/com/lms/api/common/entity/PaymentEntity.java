package com.lms.api.common.entity;

import com.lms.api.common.code.PaymentMethod;
import com.lms.api.common.code.PaymentType;
import com.lms.api.common.code.YN;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "payment")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  String id;

  LocalDate paymentDate;

  @Enumerated(EnumType.STRING)
  PaymentType type;

  @Enumerated(EnumType.STRING)
  PaymentMethod paymentMethod;

  int paymentAmount;
  String accountHolder;
  String accountNumber;
  String cardCompany;
  String cardNumber;
  Integer installmentMonths;
  String approvalNumber;
  int cancelAmount;
  LocalDate cancelDate;
  String cancelManager;
  String memo;

  @Enumerated(EnumType.STRING)
  YN isReceiptIssued = YN.N;

  String receiptNumber;
  Integer depositAmount;
  String transactionName;
  String companyNumber;
  LocalDateTime accountTransactionDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId", nullable = false)
  UserEntity userEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "orderId", nullable = false)
  OrderEntity orderEntity;
}
