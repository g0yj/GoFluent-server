package com.lms.api.common.entity;

import com.lms.api.common.code.SmsStatus;
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
import lombok.ToString;
import lombok.ToString.Exclude;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "sms_target")
public class SmsTargetEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String messageId;
  String email;
  String recipientPhone;
  String recipientName;

  @Enumerated(EnumType.STRING)
  SmsStatus status;

  LocalDateTime sendDate;

  @Exclude
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "smsId", nullable = false)
  SmsEntity smsEntity;

}
