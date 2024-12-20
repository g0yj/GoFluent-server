package com.lms.api.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "code_group")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonCodeGroupEntity extends BaseEntity {

  @Id
  @Column(name = "codeGroup")
  String codeGroup;
  String codeGroupName;

}

