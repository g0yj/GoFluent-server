package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.CreateConsultationUserResponse;
import com.lms.api.admin.controller.dto.CreateUserRequest;
import com.lms.api.admin.controller.dto.consultation.CreateConsultationHistoryRequest;
import com.lms.api.admin.controller.dto.consultation.CreateConsultationRequest;
import com.lms.api.admin.controller.dto.consultation.GetValCellphoneRequest;
import com.lms.api.admin.controller.dto.consultation.ListConsultationHistoryResponse;
import com.lms.api.admin.controller.dto.consultation.ListConsultationResponse;
import com.lms.api.admin.controller.dto.consultation.ListConsultationsRequest;
import com.lms.api.admin.controller.dto.consultation.UpdateConsultationRequest;
import com.lms.api.admin.service.ConsultationAdminService;
import com.lms.api.admin.service.dto.CreateUser;
import com.lms.api.admin.service.dto.consultation.ConsultationHistory;
import com.lms.api.admin.service.dto.consultation.ConsultationInfo;
import com.lms.api.admin.service.dto.consultation.CreateConsultation;
import com.lms.api.admin.service.dto.consultation.CreateConsultationHistory;
import com.lms.api.admin.service.dto.consultation.ListConsultation;
import com.lms.api.admin.service.dto.consultation.SearchConsultations;
import com.lms.api.admin.service.dto.consultation.UpdateConsultation;
import com.lms.api.common.controller.dto.PageResponse;
import com.lms.api.common.dto.LoginInfo;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/consultations")
@RequiredArgsConstructor
public class ConsultationAdminController {

  private final ConsultationAdminService consultationAdminService;
  private final ConsultationAdminControllerMapper consultationAdminControllerMapper;
  private final UserAdminControllerMapper userAdminControllerMapper;

  @GetMapping
  public PageResponse<ListConsultationResponse> listConsultation(
      @Valid ListConsultationsRequest request) {
    SearchConsultations searchConsultations = consultationAdminControllerMapper.toSearchConsultaions(
        request);
    Page<ListConsultation> consultationPage = consultationAdminService.listConsultations(
        searchConsultations);
    return consultationAdminControllerMapper.toListConsultationResponse(consultationPage,
        searchConsultations);
  }

  /**
   * 상담자 회원 등록
   */
  @PostMapping("/{id}/users")
  public CreateConsultationUserResponse createUser(LoginInfo loginInfo, @PathVariable Long id,
      @RequestBody @Valid CreateUserRequest request) {
    CreateUser createUser = userAdminControllerMapper.toCreateUser(request, loginInfo.getId());
    String userId = consultationAdminService.createUser(id,createUser);

    return CreateConsultationUserResponse.builder()
        .userId(userId)
        .build();
  }

  /**
   * 상담 상세 조회
   */
  @GetMapping("/{id}")
  public ConsultationInfo getConsultation(@PathVariable Long id) {
    return consultationAdminService.getConsultation(id);
  }

  /**
   * 상담 등록(파일첨부)
   */
  @PostMapping
  public void createConsultation(LoginInfo loginInfo, @Valid CreateConsultationRequest requet) {
    CreateConsultation createConsultation = consultationAdminControllerMapper.toCreateConsultation(
        requet, loginInfo.getId());
    consultationAdminService.createConsultation(createConsultation);
  }


  /**
   * 상담 수정 (파일첨부)
   */
  @PutMapping("/{id}")
  public void updateConsultation(LoginInfo loginInfo, @PathVariable Long id,
      @Valid UpdateConsultationRequest request) {
    UpdateConsultation updateConsultation = consultationAdminControllerMapper.toUpdateConsultation(
        id, request, loginInfo.getId());
    consultationAdminService.updateConsultation(updateConsultation);
  }


  @DeleteMapping("/{id}")
  public void deleteConsultation(@PathVariable Long id) {
    consultationAdminService.deleteConsultation(id);
  }

  /**
   * 추가상담이력 등록
   */

  @PostMapping("/history/{id}")
  public void createConsultationHistory(LoginInfo loginInfo, @PathVariable Long id,
      @Valid @RequestBody CreateConsultationHistoryRequest request) {
    CreateConsultationHistory consultationHistory = consultationAdminControllerMapper.toCreateConsultationHitory(
        id, request, loginInfo.getId());
    consultationAdminService.createConsultationHistory(consultationHistory);
  }

  @DeleteMapping("/history/{id}")
  public void deleteConsultationHistory(@PathVariable Long id) {
    consultationAdminService.deleteConsultationHistory(id);
  }

  /**
   * 추가상담이력 목록 조회
   */
  @GetMapping("/history/{id}")
  public List<ListConsultationHistoryResponse> listConsultationHistories(@PathVariable Long id) {
    List<ConsultationHistory> consultationHistories = consultationAdminService.listConsultationHistories(
        id);
    return consultationAdminControllerMapper.toListConsultationHistoryResponse(
        consultationHistories);
  }

  /**
   * 연락처 중복 체크 - @RequestBody 사용 시 get 요청 불가
   */
  @PostMapping("/cellphone")
  public void valCellphone(@RequestBody GetValCellphoneRequest request) {
    consultationAdminService.valCellphone(request);
  }
}
