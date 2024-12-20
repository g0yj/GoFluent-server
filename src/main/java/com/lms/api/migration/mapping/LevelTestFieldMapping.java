package com.lms.api.migration.mapping;

import java.util.HashMap;
import java.util.Map;

public class LevelTestFieldMapping implements FieldMappingProvider {

  private final Map<String, FieldMapping> fieldMappings;

  public LevelTestFieldMapping() {
    fieldMappings = new HashMap<>();
    fieldMappings.put("id", new FieldMapping(0, "고유값", null));
    fieldMappings.put("userId", new FieldMapping(1, "로그인아이디", null));
    fieldMappings.put("testStartTime", new FieldMapping(2, "테스트일자", null));
    fieldMappings.put("lbt", new FieldMapping(3, "LBT", null));
    fieldMappings.put("rbt", new FieldMapping(4, "RBT", null));
    fieldMappings.put("obt", new FieldMapping(5, "OBT", null));
    fieldMappings.put("note", new FieldMapping(8, "메모", null));
    fieldMappings.put("createdOn", new FieldMapping(9, "등록일", null));
    fieldMappings.put("modifiedOn", new FieldMapping(9, "등록일", null));
    fieldMappings.put("purpose", new FieldMapping(10, "학습목적", null));
    fieldMappings.put("studyType", new FieldMapping(11, "사유", null));
    fieldMappings.put("familyBackground", new FieldMapping(13, "Background Information Family", null));
    fieldMappings.put("usageType", new FieldMapping(14, "Background Information COMPANY", null));
    fieldMappings.put("occupation", new FieldMapping(15, "Background Information OCCUPATION", null));
    fieldMappings.put("spareTime", new FieldMapping(16, "Background Information SPARETIME", null));
    fieldMappings.put("travelAbroad", new FieldMapping(17, "Background Information TRAVEL", null));
    fieldMappings.put("futurePlans", new FieldMapping(18, "Background Information FUTURE", null));
    fieldMappings.put("consonants", new FieldMapping(19, "PRON_CONSONANTS", null));
    fieldMappings.put("vowels", new FieldMapping(20, "PRON_VOWELS", null));
    fieldMappings.put("clarity", new FieldMapping(21, "PRON_CLARITY", null));
    fieldMappings.put("intonation", new FieldMapping(22, "PRON_INTONATION", null));
    fieldMappings.put("vocabulary", new FieldMapping(23, "VOCABULARY", null));
    fieldMappings.put("verbsTense", new FieldMapping(24, "GRAMER_VERBS", null));
    fieldMappings.put("agreement", new FieldMapping(25, "GRAMER_AGREEMENT", null));
    fieldMappings.put("prepositions", new FieldMapping(26, "GRAMER_PREPOSITIONS", null));
    fieldMappings.put("articles", new FieldMapping(27, "GRAMER_ARTICLES", null));
    fieldMappings.put("plurals", new FieldMapping(28, "GRAMER_PLURALS", null));
    fieldMappings.put("others", new FieldMapping(29, "GRAMER_OTHERS", null));
    fieldMappings.put("strongPoint", new FieldMapping(30, "STRONGPOINT", null));
    fieldMappings.put("weakPoint", new FieldMapping(31, "WEAKPOINT", null));
    fieldMappings.put("comprehension", new FieldMapping(32, "COMPREHENSION", null));
    fieldMappings.put("confidence", new FieldMapping(33, "CONFIDENCE", null));
    fieldMappings.put("comments", new FieldMapping(34, "COMMENTS", null));
    fieldMappings.put("recommendedLevel", new FieldMapping(35, "LEVEL", null));
    fieldMappings.put("recommendedLevelEtc", new FieldMapping(36, "LEVEL_COMM", null));
    fieldMappings.put("interviewer", new FieldMapping(37, "INTERVIEWER", null));
  }

  @Override
  public FieldMapping getFieldMapping(String fieldName) {
    return fieldMappings.get(fieldName);
  }
}
