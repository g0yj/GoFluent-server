package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.statistics.GetLdfResponse;
import com.lms.api.admin.controller.dto.statistics.GetSmsResponse;
import com.lms.api.admin.controller.dto.statistics.ListEvaluationLdfResponse;
import com.lms.api.admin.controller.dto.statistics.ListEvaluationRequest;
import com.lms.api.admin.controller.dto.statistics.ListEvaluationResponse;
import com.lms.api.admin.controller.dto.statistics.ListLdfResponse;
import com.lms.api.admin.controller.dto.statistics.ListSmsRequest;
import com.lms.api.admin.controller.dto.statistics.ListSmsSuccessResponse;
import com.lms.api.admin.controller.dto.statistics.ListSmsTargetRequest;
import com.lms.api.admin.controller.dto.statistics.ListSmsTargetResponse;
import com.lms.api.admin.controller.dto.statistics.ListSmsWaitingRequest;
import com.lms.api.admin.controller.dto.statistics.ListSmsWaitingResponse;
import com.lms.api.admin.controller.dto.statistics.ListUserRequest;
import com.lms.api.admin.controller.dto.statistics.ListUserResponse;
import com.lms.api.admin.service.dto.Ldf;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.statistics.Evaluation;
import com.lms.api.admin.service.dto.statistics.GetLdf;
import com.lms.api.admin.service.dto.statistics.GetSms;
import com.lms.api.admin.service.dto.statistics.SearchEvaluation;
import com.lms.api.admin.service.dto.statistics.SearchListSms;
import com.lms.api.admin.service.dto.statistics.SearchListUser;
import com.lms.api.admin.service.dto.statistics.SearchSmsTarget;
import com.lms.api.admin.service.dto.statistics.Sms;
import com.lms.api.admin.service.dto.statistics.SmsTarget;
import com.lms.api.admin.service.dto.statistics.SuccessSms;
import com.lms.api.admin.service.dto.statistics.WaitingSms;
import com.lms.api.common.controller.dto.PageResponse;
import com.lms.api.common.entity.SmsEntity;
import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {
    ControllerMapper.class})
public interface StatisticsAdminControllerMapper {

  SearchListSms toSearchSms(ListSmsRequest request);

  SearchListSms toSearchSms(ListSmsWaitingRequest request);


  @Mapping(target = "list", source = "smsPage.content")
  @Mapping(target = "totalCount", source = "smsPage.totalElements")
  PageResponse<ListSmsSuccessResponse> toListSmsResponse(Page<SuccessSms> smsPage, SearchListSms searchSms);

  @Mapping(target = "list", source = "smsPage.content")
  @Mapping(target = "totalCount", source = "smsPage.totalElements")
  PageResponse<ListSmsWaitingResponse> toListSmsWaitingResponse(Page<WaitingSms> smsPage, SearchListSms searchSms);

  @Mapping(target = "id" , source = "sms.id")
  @Mapping(target = "senderName" , source = "sms.senderName")
  @Mapping(target = "content" , source = "sms.content")
  @Mapping(target = "sendDate" , source = "sms.sendDate")
  @Mapping(target = "reservationDate" , source = "sms.reservationDate")
  @Mapping(target = "senderPhone" , source = "sms.senderPhone")
  @Mapping(target = "sendType" , source = "sms.sendType")
  ListSmsSuccessResponse toListSmsResponse(SuccessSms successSms);

  @Mapping(target = "senderName" , source = "sms.senderName")
  @Mapping(target = "senderPhone" , source = "sms.senderPhone")
  @Mapping(target = "content" , source = "sms.content")
  @Mapping(target = "count" , source = "sms.count")
  ListSmsWaitingResponse toListSmsWaitingResponse(WaitingSms sms);

  Sms toSms(SmsEntity smsEntity);

  /**
   * 발송 내역 상세 조회
   */
  GetSmsResponse toGetSmsResponse(GetSms getSms);

  /**
   * 대상자 보기
   */
  @Mapping(target = "smsId", source = "id")
  SearchSmsTarget toSearchSmsTarget(long id, ListSmsTargetRequest request);

  @Mapping(target = "list", source = "smsTargetPage.content")
  @Mapping(target = "totalCount", source = "smsTargetPage.totalElements")
  PageResponse<ListSmsTargetResponse> toListSmsTargetResponse(Page<SmsTarget> smsTargetPage,
      SearchSmsTarget searchSmsTarget);

  ListSmsTargetResponse toListSmsTargetResponse(SmsTarget smsTarget);

  /**
   * SMS 발송 대상자 목록
   */
  SearchListUser toSearchListUser(ListUserRequest listUserRequest);

  List<ListUserResponse> toListUserResponse(List<User> listUser);

  /**
   * 평가 현황
   */
  SearchEvaluation toSearchEvaluation(ListEvaluationRequest toSearchEvaluation);

  List<ListEvaluationResponse> toListEvaluationResponse(List<Evaluation> evaluations);

  @Mapping(target = "date", source = "reservation.date")
  @Mapping(target = "startTime", source = "reservation.startTime")
  @Mapping(target = "endTime", source = "reservation.endTime")
  @Mapping(target = "userName", source = "user.name")
  ListLdfResponse toListLdfResponse(Ldf ldf);

  /**
   * 평가현황 강사 별 상세
   */
  @Mapping(target = "teacherId", source = "id")
  GetLdf toGetLdf(String id);

  @Mapping(target = "list", source = "getLdfPage.content")
  @Mapping(target = "totalCount", source = "getLdfPage.totalElements")
  PageResponse<ListEvaluationLdfResponse> toListEvaluationLdfResponse(Page<GetLdf> getLdfPage,
      GetLdf getLdf);

  @Mapping(target = "id", source = "ldf.id")
  @Mapping(target = "date", source = "reservation.date")
  @Mapping(target = "endTime", source = "reservation.endTime")
  @Mapping(target = "startTime", source = "reservation.startTime")
  @Mapping(target = "studentName", source = "user.name")
  @Mapping(target = "grade", source = "ldf.grade")
  @Mapping(target = "evaluation", source = "ldf.evaluation")
  ListEvaluationLdfResponse toListEvaluationLdfResponse(GetLdf getLdf);

  /**
   * ldf 상세조회
   */
  @Mapping(target = "id", source = "ldf.id")
  @Mapping(target = "lesson", source = "ldf.lesson")
  @Mapping(target = "contentSp", source = "ldf.contentSp")
  @Mapping(target = "contentV", source = "ldf.contentV")
  @Mapping(target = "contentSg", source = "ldf.contentSg")
  @Mapping(target = "contentC", source = "ldf.contentC")
  GetLdfResponse toGetLdf(GetLdf getLdf);
}
