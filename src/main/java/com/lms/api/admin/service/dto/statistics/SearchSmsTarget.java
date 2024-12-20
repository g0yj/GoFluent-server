package com.lms.api.admin.service.dto.statistics;

import com.lms.api.common.service.dto.Search;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchSmsTarget extends Search {

  Long smsId;

}
