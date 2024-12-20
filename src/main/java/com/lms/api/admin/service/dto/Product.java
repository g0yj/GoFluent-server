package com.lms.api.admin.service.dto;

import com.lms.api.common.code.LanguageCode;
import com.lms.api.common.code.LessonType;
import com.lms.api.common.code.ProductType;
import com.lms.api.common.code.YN;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

  String id;
  ProductType type;
  LanguageCode language;
  LessonType lessonType;

  String quantityUnit;
  String options;

  String name;
  int price;
  YN curriculumYN;
  YN shortCourseYN;
  @Builder.Default
  int sort = 0;


  public List<Option> getOptions() {
    if (options == null || options.isEmpty()) {
      return List.of();
    }

    return Arrays.stream(options.split(","))
        .map(option -> Option.builder()
            .value(option)
            .label(option.split(":")[1])
            .build())
        .toList();
  }

  public Option getOption(String option) {
    return getOptions().stream().filter(o -> o.getValue().equals(option))
        .findFirst().orElse(Option.builder().label("").build());
  }

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Option {

    String value;
    String label;
  }
}
