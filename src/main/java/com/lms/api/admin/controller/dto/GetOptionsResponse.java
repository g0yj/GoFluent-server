package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetOptionsResponse {

  List<Option> teachers;
  List<Option> consultants;
  List<Option> memberConsultationTypes;
  List<Option> cardCompanies;

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Option {

    String value;
    String label;
  }
}
