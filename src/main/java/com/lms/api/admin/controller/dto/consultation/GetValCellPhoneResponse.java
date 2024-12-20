package com.lms.api.admin.controller.dto.consultation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetValCellPhoneResponse {
  String message;

  public GetValCellPhoneResponse(String message) {
    this.message = message;
  }
}
