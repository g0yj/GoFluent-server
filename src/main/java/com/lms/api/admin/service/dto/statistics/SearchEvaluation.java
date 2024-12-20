package com.lms.api.admin.service.dto.statistics;

import com.lms.api.common.service.dto.Search;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchEvaluation extends Search {

  LocalDate date; // 수업일

}
