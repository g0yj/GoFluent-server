package com.lms.api.migration.mapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class FieldMapping {

  private int cvsFieldSeq;
  private String cvsFieldName;
  private Object defaultValue;
}
