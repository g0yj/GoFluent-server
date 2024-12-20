package com.lms.api.admin.service.dto;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchOrders {

  LocalDate dateFrom;
  LocalDate dateTo;
  String creatorName;
}
