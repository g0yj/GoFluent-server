package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.reservation.ReservationUserRequest;
import com.lms.api.admin.controller.dto.statistics.DeleteCancelSmsRequest;
import com.lms.api.admin.controller.dto.statistics.DeleteSmsRequest;
import com.lms.api.admin.controller.dto.statistics.GetLdfResponse;
import com.lms.api.admin.controller.dto.statistics.GetSmsResponse;
import com.lms.api.admin.controller.dto.statistics.ListEvaluationRequest;
import com.lms.api.admin.controller.dto.statistics.ListLdfRequest;
import com.lms.api.admin.controller.dto.statistics.ListSmsRequest;
import com.lms.api.admin.controller.dto.statistics.ListSmsSuccessResponse;
import com.lms.api.admin.controller.dto.statistics.ListSmsTargetRequest;
import com.lms.api.admin.controller.dto.statistics.ListSmsTargetResponse;
import com.lms.api.admin.controller.dto.statistics.ListSmsWaitingRequest;
import com.lms.api.admin.controller.dto.statistics.ListSmsWaitingResponse;
import com.lms.api.admin.controller.dto.statistics.ListUserRequest;
import com.lms.api.admin.controller.dto.statistics.ListUserResponse;
import com.lms.api.admin.service.StatisticsAdminService;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.reservation.ReservationUser;
import com.lms.api.admin.service.dto.statistics.Evaluation;
import com.lms.api.admin.service.dto.statistics.GetLdf;
import com.lms.api.admin.service.dto.statistics.GetSms;
import com.lms.api.admin.service.dto.statistics.ListLdf;
import com.lms.api.admin.service.dto.statistics.SearchEvaluation;
import com.lms.api.admin.service.dto.statistics.SearchListSms;
import com.lms.api.admin.service.dto.statistics.SearchListUser;
import com.lms.api.admin.service.dto.statistics.SearchSmsTarget;
import com.lms.api.admin.service.dto.statistics.SmsTarget;
import com.lms.api.admin.service.dto.statistics.SuccessSms;
import com.lms.api.admin.service.dto.statistics.WaitingSms;
import com.lms.api.common.controller.dto.PageResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/statistics")
@RequiredArgsConstructor
public class StatisticsAdminController {

  private final StatisticsAdminService statisticsAdminService;
  private final StatisticsAdminControllerMapper statisticsAdminControllerMapper;

  /**
   * SMS 발송 관리 > 발송 내역 목록 조회: SmsEntity에 status를 사용 > (WAITING: 대기, SUCCESS:성공, FAIL: 실패) 중 성공만 출력.
   */
  @GetMapping("/successSms")
  public PageResponse<ListSmsSuccessResponse> listSms(ListSmsRequest request) {
    SearchListSms searchListSms = statisticsAdminControllerMapper.toSearchSms(request);
    Page<SuccessSms> smsPage = statisticsAdminService.toListSms(searchListSms);
    return statisticsAdminControllerMapper.toListSmsResponse(smsPage, searchListSms);
  }

  /**
   * SMS 발송 관리 > 발송 내역 상세 조회
   */
  @GetMapping("/sms/{id}")
  public GetSmsResponse getSms(@PathVariable long id) {
    GetSms sms = statisticsAdminService.getSms(id);
    return statisticsAdminControllerMapper.toGetSmsResponse(sms);
  }

  /**
   * SMS 발송 관리 > 발송 내역 삭제 - 여러개 삭제 가능하도록
   */
  @DeleteMapping("/sms")
  public void deleteSms(@RequestBody DeleteSmsRequest id) {
    statisticsAdminService.deleteSms(id);
  }

  /**
   * SMS 발송 관리 > 발송내역 > 대상자 보기 -> 메시지 식별키 누르면 리스트 출력
   */
  @GetMapping("/sms/{id}/target")
  public PageResponse<ListSmsTargetResponse> listSmsTarget(@PathVariable long id,
      ListSmsTargetRequest request) {
    SearchSmsTarget searchListSmsTarget = statisticsAdminControllerMapper.toSearchSmsTarget(id,
        request);
    Page<SmsTarget> smsTargetPage = statisticsAdminService.toListSmsTarget(searchListSmsTarget);
    return statisticsAdminControllerMapper.toListSmsTargetResponse(smsTargetPage,
        searchListSmsTarget);

  }

  /**
   * SMS 발송 관리 > 발송내역 > 내용 클릭 시 나오는 재전송 발송대상 목록
   */
  @GetMapping("/sms/user")
  public List<ListUserResponse> listUser(ListUserRequest listUserRequest) {
    SearchListUser searchListUser = statisticsAdminControllerMapper.toSearchListUser(
        listUserRequest);
    List<User> userList = statisticsAdminService.listUser(searchListUser);
    return statisticsAdminControllerMapper.toListUserResponse(userList);
  }
  /**
   * SMS 발송 관리 > 대기 내역
   * SmsTargetEntity의 status를 사용 > (WAITING: 대기, SUCCESS:성공, FAIL: 실패) 중 대기만 출력. (1:n 관계로 각 수신자에게 전달된 결과를 조회하는 api를 호출하고, 스케줄링 로직을 추가해 상태 업데이트)
   * 조건검색 (구분 : 전체,LMS,SMS / 검색 : 전체, 수신번호, 발신번호, 내용)
   */
  @GetMapping("/waitingSms")
  public PageResponse<ListSmsWaitingResponse> listWaitingSms(ListSmsWaitingRequest request) {
    SearchListSms searchListSms = statisticsAdminControllerMapper.toSearchSms(request);
    Page<WaitingSms> smsPage = statisticsAdminService.listWaitingSms(searchListSms);
    return statisticsAdminControllerMapper.toListSmsWaitingResponse(smsPage, searchListSms);
  }
  /** SMS 예약 취소 */
  @DeleteMapping("/cancelSms")
  public void deleteCancelSms(@RequestBody DeleteCancelSmsRequest cancelList){
    statisticsAdminService.deleteCancelSms(cancelList);
  }

  /**
   * 평가현황  목록
   */
  @GetMapping("/evaluations")
  public List<Evaluation> listEvaluation(ListEvaluationRequest listEvaluationRequest) {
    SearchEvaluation searchEvaluation = statisticsAdminControllerMapper.toSearchEvaluation(
        listEvaluationRequest);
    return statisticsAdminService.listEvaluation(searchEvaluation);
  }

  /** 해당(월)기간 평가된 LDF*/
  @GetMapping("/evaluations/ldf")
  public List<ListLdf> listLdfs (@Valid ListLdfRequest request){
    return statisticsAdminService.listLdfs(request);
  }

  /**
   * Ldf 상세 보기
   * 평가현황 > 목록 > 상세보기 > 출력되는 목록들 중 ldf
   */
  @GetMapping("/evaluations/{id}")
  public GetLdfResponse getLdf(@PathVariable long id) {
    GetLdf getLdf = statisticsAdminService.getLdf(id);
    return statisticsAdminControllerMapper.toGetLdf(getLdf);
  }

  /** 해당 기간 ldf 평가가 있는 유저 정보
   * @param dateFrom, dateTo
   */
  @GetMapping("/evaluations/users")
  public List<ReservationUser> listEvaluationsUser(@Valid ReservationUserRequest request){
    return statisticsAdminService.listEvaluationsUser(request);
  }
}
