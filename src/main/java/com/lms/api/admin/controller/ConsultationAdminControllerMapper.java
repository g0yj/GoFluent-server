package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.consultation.CreateConsultationHistoryRequest;
import com.lms.api.admin.controller.dto.consultation.CreateConsultationRequest;
import com.lms.api.admin.controller.dto.consultation.ListConsultationHistoryResponse;
import com.lms.api.admin.controller.dto.consultation.ListConsultationResponse;
import com.lms.api.admin.controller.dto.consultation.ListConsultationsRequest;
import com.lms.api.admin.controller.dto.consultation.UpdateConsultationRequest;
import com.lms.api.admin.service.dto.consultation.ConsultationHistory;
import com.lms.api.admin.service.dto.consultation.CreateConsultation;
import com.lms.api.admin.service.dto.consultation.CreateConsultationHistory;
import com.lms.api.admin.service.dto.consultation.ListConsultation;
import com.lms.api.admin.service.dto.consultation.SearchConsultations;
import com.lms.api.admin.service.dto.consultation.UpdateConsultation;
import com.lms.api.common.controller.dto.PageResponse;
import com.lms.api.common.entity.ConsultationHistoryEntity;
import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import com.lms.api.common.service.dto.Search;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {
    ControllerMapper.class})
public interface ConsultationAdminControllerMapper {

  SearchConsultations toSearchConsultaions(ListConsultationsRequest listConsultationsRequest);

  @Mapping(target = "list", source = "consultationPage.content")
  @Mapping(target = "totalCount", source = "consultationPage.totalElements")
  PageResponse<ListConsultationResponse> toListConsultationResponse(
      Page<ListConsultation> consultationPage, Search search);

  @Mapping(target = "smsList" , source = "smsList")
  @Mapping(target = "status" , source = "status")
  @Mapping(target = "callTime" , source = "callTime")
  @Mapping(target = "createdOn" , source = "createdOn")
  ListConsultationResponse toListConsultationResponse(ListConsultation consultation);

  @Mapping(target = "multipartFile", source = "request.file")
  @Mapping(target = "file", ignore = true)
  @Mapping(target = "createdBy", source = "createdBy")
  @Mapping(target = "type", source = "request.type")
  @Mapping(target = "studyPurposes", source = "request.studyPurposes")
  CreateConsultation toCreateConsultation(CreateConsultationRequest request, String createdBy);


  @Mapping(target = "multipartFile", source = "request.file")
  @Mapping(target = "file", ignore = true)
  @Mapping(target = "foundPath", source = "request.foundPath")
  @Mapping(target = "callTime", source = "request.callTime")
  UpdateConsultation toUpdateConsultation(Long id, UpdateConsultationRequest request,
      String modifiedBy);

  /** 추가상담이력등록*/
  @Mapping(target = "consultationId", source = "id")
  @Mapping(target = "createdBy", source = "createdBy")
  CreateConsultationHistory toCreateConsultationHitory(Long id,
      CreateConsultationHistoryRequest request, String createdBy);



  /** 추가 상담이력 목록 */
  List<ListConsultationHistoryResponse> toListConsultationHistoryResponse(
      List<ConsultationHistory> consultationHistories);

  ConsultationHistory toListConsultationHistoryResponse(
      ConsultationHistoryEntity consultationHistory);
}