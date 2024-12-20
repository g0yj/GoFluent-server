package com.lms.api.admin.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetUserLevelTest {

  Long id; // 레벨 테스트 식별키
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDateTime testStartTime;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
  LocalDateTime testEndTime;
  String interviewer;
  String lbt;
  String rbt;
  String obt;
  String testIp;
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

  String file;
  String originalFile;
  String fileUrl;
  Long fileSize;

}