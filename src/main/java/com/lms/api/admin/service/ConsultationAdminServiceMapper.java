package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.consultation.ConsultationHistory;
import com.lms.api.admin.service.dto.consultation.CreateConsultation;
import com.lms.api.admin.service.dto.consultation.CreateConsultationHistory;
import com.lms.api.admin.service.dto.consultation.GetConsultation;
import com.lms.api.admin.service.dto.consultation.ListConsultation;
import com.lms.api.admin.service.dto.consultation.SmsList;
import com.lms.api.admin.service.dto.consultation.UpdateConsultation;
import com.lms.api.common.code.CallTime;
import com.lms.api.common.code.StudyPurpose;
import com.lms.api.common.entity.ConsultationEntity;
import com.lms.api.common.entity.ConsultationHistoryEntity;
import com.lms.api.common.entity.SmsTargetEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {ServiceMapper.class})
public interface ConsultationAdminServiceMapper {

  @Mapping(target = "smsList" , source = "smsList")
  @Mapping(target = "status" , source = "consultationEntity.status")
  @Mapping(target = "callTime" , source = "callTime")
  @Mapping(target = "createdOn" , source = "consultationEntity.createdOn")
  ListConsultation toListConsultation(ConsultationEntity consultationEntity, String creatorName , List<SmsList> smsList , CallTime callTime);


  @Mapping(target = "smsId" , source = "smsEntity.id")
  List<SmsList> toSmsList(List<SmsTargetEntity> smsTargetEntity);

  @Mapping(target = "content", source ="smsEntity.content")
  @Mapping(target = "smsId" , source = "smsEntity.id")
  @Mapping(target = "recipientPhone", source = "recipientPhone")
  @Mapping(target = "recipientName", source = "recipientName")
  @Mapping(target = "senderPhone", source = "smsTargetEntity.smsEntity.senderPhone")
  @Mapping(target = "senderName", source = "smsTargetEntity.smsEntity.senderName")
  @Mapping(target = "sendDate", source = "sendDate")
  @Mapping(target = "status", source = "status")
  SmsList toSmsList(SmsTargetEntity smsTargetEntity);
  /**
   * 상담 등록
   */
  ConsultationEntity toConsultationEntity(CreateConsultation createConsultation);


  /** 상담수정 */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "studyPurpose", source = "studyPurposes")
  @Mapping(target = "callTime", source = "callTime")
  @Mapping(target = "foundPath", source = "foundPath")
  void mapConsultationEntity(UpdateConsultation updateConsultation,
      @MappingTarget ConsultationEntity consultationEntity);


  @Mapping(target = "consultationEntity.id", source = "consultationId")
  ConsultationHistoryEntity toConsultationHistoryEntity(
      CreateConsultationHistory consultationHistory);

  @Mapping(target = "fileUrl" , source = "getUrl")
  @Mapping(target = "studyPurposes" , source = "studyPurposes")
  GetConsultation toGetConsultation(ConsultationEntity consultationEntity , List<StudyPurpose> studyPurposes, String getUrl);

  ConsultationHistory toConsultationHistory(ConsultationHistoryEntity consultationHistoryEntity);

  @Mapping(target = "consultationId", source = "consultationHistoryEntity.id")
  @Mapping(target = "date", source = "consultationHistoryEntity.modifiedOn")
  @Mapping(target = "modifiedName", source = "modifiedName")
  ConsultationHistory toConsultationHistory(ConsultationHistoryEntity consultationHistoryEntity,
      String modifiedName);

}
