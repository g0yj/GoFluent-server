package com.lms.api.common.entity;

import com.lms.api.common.code.YN;
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
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "order_product")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderProductEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  String id;

  int months;
  int quantity;

  @Enumerated(EnumType.STRING)
  YN isRetake;

  String retakeTeacherId;
  int amount;
  int discountAmount;
  int paymentAmount;
  String productOption;
  String note;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "orderId", nullable = false)
  OrderEntity orderEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "productId", nullable = false)
  ProductEntity productEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teacherId", nullable = false)
  TeacherEntity teacherEntity;

  @OneToMany(mappedBy = "orderProductEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<RefundEntity> refundEntities = new ArrayList<>();

  @OneToMany(mappedBy = "orderProductEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<CourseEntity> courseEntities = new ArrayList<>();
/*

  @Transient
  public int getTotalRefundAmount() {
    return refundEntities.stream().mapToInt(RefundEntity::getRefundAmount).sum();
  }
*/

}
