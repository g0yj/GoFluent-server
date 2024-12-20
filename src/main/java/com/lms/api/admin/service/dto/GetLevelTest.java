package com.lms.api.admin.service.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetLevelTest {

  LocalDateTime testStartTime;
  LocalDateTime testEndTime;
  String interviewer;
  String lbt;
  String rbt;
  String obt;
  String testIp;
  String file;
  String note;
  String purpose;

  String studyTypeEtc;
  String familyBackground;
  String usageType;
  String occupation;
  String spareTime;
  String travelAbroad;
  String futurePlans;

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

  String recommendedLevelEtc;


  String studyType;
  String consonants;
  String vowels;
  String recommendedLevel;

  List<String> studyTypeList;
  List<String> consonantsList;
  List<String> vowelsList;
  List<String> recommendedList;


  Long id; // 레벨 테스트 식별키
  String userId; // 회원 식별키
}
