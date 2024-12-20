package com.lms.api.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "email")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Column(name = "ldf_id")
  Long ldfId;
  String recipientEmail;
  String recipientName;
  String senderEmail;
  String senderName;
  String title;
  String content;
}
