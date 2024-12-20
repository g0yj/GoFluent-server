package com.lms.api.admin.controller.dto.product;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListManagementProductResponse {
    String id;
    String name;
    int price;
    String curriculumYN;// 여부
    String shortCourseYN;

}
