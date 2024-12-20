package com.lms.api.admin.service.dto.statistics;


import com.lms.api.admin.code.SearchSmsCode.SearchType;
import com.lms.api.common.service.dto.Search;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchListSms extends Search {

  LocalDate sendDateFrom;
  LocalDate sendDateTo;
  SearchType sendType;

}
