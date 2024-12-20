package com.lms.api.admin.service.dto;

import com.lms.api.admin.code.levelTest.TestConsonants;
import com.lms.api.admin.code.levelTest.TestRecommendLevel;
import com.lms.api.admin.code.levelTest.TestStudyType;
import com.lms.api.admin.code.levelTest.TestVowels;
import java.time.LocalDateTime;
import java.util.List;
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
public class UpdateUserLevelTest {

  LocalDateTime testStartTime;
  LocalDateTime testEndTime;
  String interviewer;
  String lbt;
  String rbt;
  String obt;
  String testIp;

  MultipartFile multipartFile;
  String file;

  Boolean isDeleteFile;
  String note;
  String purpose;

  List<TestStudyType> studyType;

  String studyTypeEtc;
  String familyBackground;
  String usageType;
  String occupation;
  String spareTime;
  String travelAbroad;
  String futurePlans;

  List<TestConsonants> consonants;
  List<TestVowels> vowels;

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
  String recommendedLevelEtc;


  String studyTypeList;
  String consonantsList;
  String vowelsList;
  String recommendedLevelList;

  Long id; // 레벨 테스트 식별키
  String userId; // 회원 식별키
  String modifiedBy;

  Long fileSize;

  public String getOriginalFile() {
    if (multipartFile == null) {
      return null;
    }

    return multipartFile.getOriginalFilename();
  }

}