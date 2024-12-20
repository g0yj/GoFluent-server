package com.lms.api.common.mapper;

import com.lms.api.common.code.AttendanceStatus;
import com.lms.api.common.code.CallTime;
import com.lms.api.common.code.CardCompany;
import com.lms.api.common.code.ConsultationStatus;
import com.lms.api.common.code.CourseHistoryType;
import com.lms.api.common.code.CourseStatus;
import com.lms.api.common.code.JoinPath;
import com.lms.api.common.code.LanguageCode;
import com.lms.api.common.code.MemberConsultationType;
import com.lms.api.common.code.PaymentMethod;
import com.lms.api.common.code.PaymentType;
import com.lms.api.common.code.ProductType;
import com.lms.api.common.code.StudyPurpose;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = ControllerMapperConfig.class)
public interface ControllerMapper {

  default String toLanguageCode(LanguageCode languageCode) {
    return languageCode.getLabel();
  }

  default String toCourseHistoryType(CourseHistoryType courseHistoryType) {
    return courseHistoryType == null ? null : courseHistoryType.getCode();
  }

  default String toAttendanceStatus(AttendanceStatus attendanceStatus) {
    return attendanceStatus == null ? null : attendanceStatus.getLabel();
  }

  default String toCourseStatus(CourseStatus courseStatus) {
    return courseStatus == null ? null : courseStatus.getLabel();
  }

  default String toMemberConsultationType(MemberConsultationType memberConsultationType) {
    return memberConsultationType == null ? null : memberConsultationType.getLabel();
  }

  default String toProductType(ProductType productType) {
    return productType == null ? null : productType.getLabel();
  }

  default String toConsultationStatus(ConsultationStatus status) {
    return status == null ? null : status.getCode();
  }

  default String toCallTime(CallTime callTime) {
    return callTime == null ? null : callTime.getCode();
  }

  default String toStudyPurpose(StudyPurpose studyPurpose) {
    return studyPurpose == null ? null : studyPurpose.getCode();
  }

  default String toJoinPath(JoinPath joinPath) {
    return joinPath == null ? null : joinPath.getCode();
  }

  default String toPaymentType(PaymentType paymentType) {
    return paymentType == null ? null : paymentType.getLabel();
  }

  default String toPaymentMethod(PaymentMethod paymentMethod) {
    return paymentMethod == null ? null : paymentMethod.getLabel();
  }

  default String toInstallmentMonths(Integer installmentMonths) {
    return (installmentMonths == null || installmentMonths < 2) ? "일시불" : installmentMonths + "개월";
  }

  default String toCardCompany(CardCompany cardCompany) {
    return cardCompany == null ? null : cardCompany.getLabel();
  }

}