package com.lms.api.common.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "code")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CodeEntity extends BaseEntity {

  @EmbeddedId
  CodeEntityId codeEntityId;

  String name;
  int sort;
  String useYn;

  @Embeddable
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @EqualsAndHashCode
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class CodeEntityId implements Serializable {
    /**
     * 차장님은 Integer 타입으로 만드셨었음..!
     * */
    String codeGroup;
    String code;
  }
}
