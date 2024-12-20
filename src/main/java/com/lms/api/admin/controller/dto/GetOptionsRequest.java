package com.lms.api.admin.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetOptionsRequest {

  @NotEmpty
  List<OptionsField> fields;

  public enum OptionsField {
    TEACHERS,
    CONSULTANTS,
    MEMBER_CONSULTATION_TYPES,
    CARD_COMPANIES
  }
}
