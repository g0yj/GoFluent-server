package com.lms.api.common.mapper;

import com.lms.api.admin.code.levelTest.TestConsonants;
import com.lms.api.admin.code.levelTest.TestRecommendLevel;
import com.lms.api.admin.code.levelTest.TestStudyType;
import com.lms.api.admin.code.levelTest.TestVowels;
import com.lms.api.common.code.CallTime;
import com.lms.api.common.code.CardCompany;
import com.lms.api.common.code.ConsultationStatus;
import com.lms.api.common.code.CourseHistoryType;
import com.lms.api.common.code.JoinPath;
import com.lms.api.common.code.MemberConsultationType;
import com.lms.api.common.code.ProductType;
import com.lms.api.common.code.YN;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class)
public interface ServiceMapper {

  default JoinPath toJoinPath(String joinPath) {
    return JoinPath.of(joinPath);
  }

  default String toJoinPath(JoinPath joinPath) {
    return JoinPath.to(joinPath);
  }

  default CourseHistoryType toCourseHistoryType(String courseHistoryType) {
    return CourseHistoryType.of(courseHistoryType);
  }

  default String toCourseHistoryType(CourseHistoryType courseHistoryType) {
    return CourseHistoryType.to(courseHistoryType);
  }

  default Boolean toBoolean(YN yn) {
    return YN.to(yn);
  }

  default YN toYN(Boolean bool) {
    return YN.of(bool);
  }

  default MemberConsultationType toMemberConsultationType(String memberConsultationType) {
    return MemberConsultationType.of(memberConsultationType);
  }

  default String toMemberConsultationType(MemberConsultationType memberConsultationType) {
    return MemberConsultationType.to(memberConsultationType);
  }

  default ProductType toProductType(String productType) {
    return ProductType.of(productType);
  }

  default String toProductType(ProductType productType) {
    return ProductType.to(productType);
  }


  default List<TestStudyType> toTestStudyTypeList(String studyType) {
    return Arrays.stream(studyType.split(","))
        .map(TestStudyType::of)
        .collect(Collectors.toList());
  }

  default List<TestConsonants> toTestConsonantsList(String consonants) {
    return Arrays.stream(consonants.split(","))
        .map(TestConsonants::of)
        .collect(Collectors.toList());
  }

  default List<TestVowels> toTestVowelsList(String vowels) {
    return Arrays.stream(vowels.split(","))
        .map(TestVowels::of)
        .collect(Collectors.toList());
  }

  default List<TestRecommendLevel> toTestRecommendLevelList(String recommendedLevel) {
    return Arrays.stream(recommendedLevel.split(","))
        .map(TestRecommendLevel::of)
        .collect(Collectors.toList());
  }

  default CardCompany toCardCompany(String cardCompany) {
    return CardCompany.of(cardCompany);
  }

  default ConsultationStatus toConsultationStatus(String consultationStaus) {
    return ConsultationStatus.of(consultationStaus);
  }

  default String toConsultationStatus(ConsultationStatus consulatation) {
    return ConsultationStatus.to(consulatation);
  }

  default CallTime toCallTime(String callTime) {return CallTime.of(callTime);}

  default String toCallTime(CallTime callTime) {
    return CallTime.to(callTime);
  }
}
