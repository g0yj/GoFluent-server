package com.lms.api.common.entity;

import com.lms.api.common.code.AddressType;
import com.lms.api.common.code.Gender;
import com.lms.api.common.code.PhoneType;
import com.lms.api.common.code.UserType;
import com.lms.api.common.code.YN;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "user_")
@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  String id;

  String loginId;
  String name;
  String password;

  @Enumerated(EnumType.STRING)
  UserType type;

  @Enumerated(EnumType.STRING)
  Gender gender;

  String cellPhone;

  @Enumerated(EnumType.STRING)
  YN isReceiveSms;

  String email;

  @Enumerated(EnumType.STRING)
  YN isReceiveEmail;

  String address;
  String detailedAddress;


  @Enumerated(EnumType.STRING)
  YN isOfficeWorker;

  @Column(name = "is_active")
  boolean active;

  String note;

  String coursePurpose;


  @ToString.Exclude
  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<OrderEntity> orderEntities = new ArrayList<>();

  @ToString.Exclude
  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<MemberConsultationEntity> memberConsultationEntities = new ArrayList<>();

  @ToString.Exclude
  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<PaymentEntity> paymentEntities = new ArrayList<>();

  @ToString.Exclude
  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<RefundEntity> refundEntities = new ArrayList<>();

  @ToString.Exclude
  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<CourseEntity> courseEntities = new ArrayList<>();

  @ToString.Exclude
  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<ReservationEntity> reservationEntities = new ArrayList<>();

  @ToString.Exclude
  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<UserLoginEntity> userLoginEntities = new ArrayList<>();

  @ToString.Exclude
  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<LdfEntity> ldfEntities = new ArrayList<>();

  @ToString.Exclude
  @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<LevelTestEntity> levelTestEntities = new ArrayList<>();
}
