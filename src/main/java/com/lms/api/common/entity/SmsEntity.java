package com.lms.api.common.entity;

import com.lms.api.admin.code.SearchSmsCode.SearchType;
import com.lms.api.common.code.SmsStatus;
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
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "sms")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmsEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String senderPhone;
  String senderName;
  String content;
  LocalDateTime reservationDate;

  @Enumerated(EnumType.STRING)
  SearchType sendType;

  @Enumerated(EnumType.STRING)
  SmsStatus status;

  LocalDateTime sendDate;
  String requestId;

  @OneToMany(mappedBy = "smsEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<SmsTargetEntity> smsTargetEntities;

}
