package com.lms.api.admin.service.dto.consultation;


import com.lms.api.common.service.dto.Search;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchConsultations extends Search {

  LocalDate createDateFrom; // 등록일 (상담일이랑다름)
  LocalDate createDateTo;
  LocalDate visitDateFrom;
  LocalDate visitDateTo;

  String type;
  String status;


}
