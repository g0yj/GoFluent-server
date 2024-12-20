package com.lms.api.admin.controller.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListCommonCodesResponse {

  List<CommonCode> commonCode;

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class CommonCode {
    String codeGroup;
    String codeGroupName;
    String code;
    String name;
    int sort;
    String useYn;
  }
}
