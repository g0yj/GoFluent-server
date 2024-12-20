package com.lms.api.admin.service.dto;

import com.lms.api.admin.code.levelTest.TestConsonants;
import com.lms.api.admin.code.levelTest.TestRecommendLevel;
import com.lms.api.admin.code.levelTest.TestStudyType;
import com.lms.api.admin.code.levelTest.TestVowels;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserLevelTest {

  LocalDateTime testStartTime;
  LocalDateTime testEndTime;
  String interviewer;
  String lbt;
  String rbt;
  String obt;
  String testIp;
  MultipartFile multipartFile;
  String file;
  String note;
  String purpose;
  List<TestStudyType> studyType;
  String studyTypeList;

  String studyTypeEtc;
  String familyBackground;
  String usageType;
  String occupation;
  String spareTime;
  String travelAbroad;
  String futurePlans;

  List<TestConsonants> consonants;
  String consonantsList;
  List<TestVowels> vowels;
 String vowelsList;

  String clarity;
  String intonation;

  String vocabulary;

  String verbsTense;
  String agreement;
  String prepositions;
  String articles;
  String plurals;
  String others;


  String strongPoint;
  String weakPoint;
  String comprehension;
  String confidence;
  String comments;

  List<TestRecommendLevel> recommendedLevel;
  String recommendedLevelList;

  String recommendedLevelEtc;

  //추가
    Long fileSize;


  String userId; // 회원 식별키
  String createdBy;

  public String getOriginalFile() {
    if (multipartFile == null) {
      return null;
    }

    return multipartFile.getOriginalFilename();
  }

    public String getRecommendedLevelList() {
        if (recommendedLevel == null) {
            return null;
        }

        return recommendedLevel.stream()
                .filter(Objects::nonNull)
                .map(TestRecommendLevel::to)
                .collect(Collectors.joining(","));
    }

    public String getVowelsList() {
        if (vowels == null) {
            return null;
        }

        return vowels.stream()
                .filter(Objects::nonNull)
                .map(TestVowels::to)
                .collect(Collectors.joining(","));
    }

    public String getConsonantsList() {
        if (consonants == null) {
            return null;
        }

        return consonants.stream()
                .filter(Objects::nonNull)
                .map(TestConsonants::to)
                .collect(Collectors.joining(","));
    }

    public String getStudyTypeList() {
        if (studyType == null) {
            return null;
        }

        return studyType.stream()
                .filter(Objects::nonNull)
                .map(TestStudyType::to)
                .collect(Collectors.joining(","));
    }
}