package com.lms.api.admin.service.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetUserLevelTestInfo {

  GetUserLevelTest levelTest;

  List<String> recommendedLevel;
  List<String> vowels;
  List<String> consonants;
  List<String> studyType;

  public GetUserLevelTestInfo(GetUserLevelTest levelTest, List<String> recommendedLevel,
      List<String> vowels, List<String> consonants, List<String> studyType) {
    this.levelTest = levelTest;
    this.recommendedLevel = recommendedLevel;
    this.vowels = vowels;
    this.consonants = consonants;
    this.studyType = studyType;
  }
}