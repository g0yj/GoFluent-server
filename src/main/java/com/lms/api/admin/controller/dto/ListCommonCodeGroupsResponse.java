package com.lms.api.admin.controller.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListCommonCodeGroupsResponse {

  List<CommonCodeGroup> commonCodeGroup;

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class CommonCodeGroup {

    String codeGroup;
    String codeGroupName;
  }
}
