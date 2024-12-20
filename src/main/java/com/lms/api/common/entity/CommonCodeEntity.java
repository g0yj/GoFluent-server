package com.lms.api.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "code")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@IdClass(CodePK.class)
public class CommonCodeEntity extends BaseEntity {

  @Id
  @Column(name = "codeGroup")
  String codeGroup;
  String codeGroupName;

  @Id
  @Column(name = "code")
  String code;
  String name;
  int sort;
  String useYn;

}

