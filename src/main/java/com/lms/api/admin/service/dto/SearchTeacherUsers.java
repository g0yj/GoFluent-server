package com.lms.api.admin.service.dto;

import com.lms.api.common.service.dto.Search;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchTeacherUsers extends Search {

    boolean today; // 학생(student) 전체: false, 오늘예약: true
    String teacherId;
}
