package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.template.CreateTemplateRequest;
import com.lms.api.admin.controller.dto.template.ListReportExcelRequest;
import com.lms.api.admin.controller.dto.template.ListReportExcelResponse;
import com.lms.api.admin.controller.dto.template.ListSmsExcelRequest;
import com.lms.api.admin.controller.dto.template.ListSmsExcelResponse;
import com.lms.api.admin.controller.dto.template.ListUsersExcelRequest;
import com.lms.api.admin.controller.dto.template.ListUsersExcelResponse;
import com.lms.api.admin.controller.dto.template.UpdateTemplateRequest;
import com.lms.api.admin.service.TemplateAdminService;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.template.CreateTemplate;
import com.lms.api.admin.service.dto.template.MemberRegisterTemplate;
import com.lms.api.admin.service.dto.template.SimpleTemplate;
import com.lms.api.admin.service.dto.template.UpdateTemplate;
import com.lms.api.common.dto.LoginInfo;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/templates")
@RequiredArgsConstructor
public class TemplateAdminController {

  private final TemplateAdminService templateAdminService;
  private final TemplateAdminControllerMapper templateAdminControllerMapper;


  /** 단순 템플릿 목록 > 식별키, 템플릿 설명, 템플릿 내용만 확인 가능*/
  @GetMapping("/simple")
  public List<SimpleTemplate> listSimpleTemplate() {
    return templateAdminService.listSimpleTemplate();
  }


  /** 템플릿 등록*/
  @PostMapping
  public void createTemplate(LoginInfo loginInfo,
      @RequestBody @Valid CreateTemplateRequest request) {
    CreateTemplate createTemplate = templateAdminControllerMapper.toCreateTemplate(request, loginInfo.getId());
    templateAdminService.createTemplate(createTemplate);
  }

  /** 템플릿 상세 조회*/
  @GetMapping("/{id}")
  public MemberRegisterTemplate getTemplate(@PathVariable long id) {
    return templateAdminService.getTemplate(id);
  }

  /** 템플릿 수정*/
  @PutMapping("/{id}")
  public void updateTemplate(LoginInfo loginInfo, @PathVariable long id,
      @RequestBody UpdateTemplateRequest request) {
    UpdateTemplate updateTemplate = templateAdminControllerMapper.toUpdateTemplate(request, id, loginInfo.getId());
    templateAdminService.updateTemplate(updateTemplate);

  }
  /** 템플릿 삭제*/
  @DeleteMapping("/{id}")
  public void deleteTemplate(@PathVariable long id) {
    templateAdminService.deleteTemplate(id);
  }

  /** 학사보고서 엑셀 다운로드를 위한 전체 목록 조회 api*/
  @GetMapping("/excel/report")
  public List<ListReportExcelResponse> listReportExcel(LoginInfo loginInfo, ListReportExcelRequest request){
    String loginId = loginInfo.getId();
    return templateAdminService.listReportExcel(loginId, request);
  }

  /** 회원 엑셀 다운로드를 위한 전체 목록 조회 api */
  @GetMapping("/excel/users")
  public List<ListUsersExcelResponse> listUserExcel(ListUsersExcelRequest request) {
    List<User> users = templateAdminService.listUserExcel(request);
    return templateAdminControllerMapper.toListUserExcelResponse(users);
  }

  /** sms 엑셀 다운로드를 위한 전체 목록 조회 api*/
  @GetMapping("/excel/sms")
  public List<ListSmsExcelResponse> listSmsExcel(ListSmsExcelRequest request) {
    return templateAdminService.listSmsExcel(request);
  }
}
