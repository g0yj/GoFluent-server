package com.lms.api.admin.controller.dto;

import com.lms.api.admin.code.levelTest.TestConsonants;
import com.lms.api.admin.code.levelTest.TestRecommendLevel;
import com.lms.api.admin.code.levelTest.TestStudyType;
import com.lms.api.admin.code.levelTest.TestVowels;
import com.lms.api.admin.service.dto.GetUserLevelTest;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetUserLevelTestResponse {

  GetUserLevelTest levelTest;

  List<TestStudyType> studyType;
  List<TestConsonants> consonants;
  List<TestVowels> vowels;
  List<TestRecommendLevel> recommendedLevel;


}
