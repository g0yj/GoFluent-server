package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.CreateCommonCodeRequest;
import com.lms.api.admin.controller.dto.GetOptionsRequest;
import com.lms.api.admin.controller.dto.GetOptionsRequest.OptionsField;
import com.lms.api.admin.controller.dto.GetOptionsResponse;
import com.lms.api.admin.controller.dto.ListCommonCodeGroupsResponse;
import com.lms.api.admin.controller.dto.ListCommonCodesResponse;
import com.lms.api.admin.controller.dto.TestUploadRequest;
import com.lms.api.admin.controller.dto.TestUploadResponse;
import com.lms.api.admin.controller.dto.UpdateCommonCodeRequest;
import com.lms.api.admin.controller.dto.common.CodeList;
import com.lms.api.admin.service.CommonCodeService;
import com.lms.api.admin.service.TeacherAdminService;
import com.lms.api.admin.service.UserAdminService;
import com.lms.api.admin.service.dto.CommonCode;
import com.lms.api.admin.service.dto.CommonCodeGroup;
import com.lms.api.admin.service.dto.CreateCommonCode;
import com.lms.api.admin.service.dto.Login;
import com.lms.api.admin.service.dto.UpdateCommonCode;
import com.lms.api.common.code.CardCompany;
import com.lms.api.common.code.LoginType;
import com.lms.api.common.code.MemberConsultationType;
import com.lms.api.common.controller.dto.LoginRequest;
import com.lms.api.common.controller.dto.LoginResponse;
import com.lms.api.common.dto.LoginInfo;
import com.lms.api.common.service.FileService;
import com.lms.api.common.service.LoginService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

  private final TeacherAdminService teacherAdminService;
  private final UserAdminService userAdminService;
  private final LoginService loginService;
  private final AdminControllerMapper adminControllerMapper;
  private final CommonCodeService commonCodeService;
  private final FileService fileService;

  @GetMapping("/options")
  public GetOptionsResponse getOptions(@Valid GetOptionsRequest request) {
    GetOptionsResponse.GetOptionsResponseBuilder response = GetOptionsResponse.builder();

    if (request.getFields().contains(OptionsField.TEACHERS)) {
      response.teachers(teacherAdminService.listTeachers().stream()
          .map(adminControllerMapper::toOption)
          .toList());
    }

    if (request.getFields().contains(OptionsField.CONSULTANTS)) {
      response.consultants(userAdminService.listConsultants().stream()
          .map(adminControllerMapper::toOption)
          .toList());
    }

    if (request.getFields().contains(OptionsField.MEMBER_CONSULTATION_TYPES)) {
      response.memberConsultationTypes(Arrays.stream(MemberConsultationType.values())
          .map(memberConsultationType -> GetOptionsResponse.Option.builder()
              .value(memberConsultationType.name())
              .label(memberConsultationType.getLabel())
              .build())
          .toList());
    }

    if (request.getFields().contains(OptionsField.CARD_COMPANIES)) {
      response.cardCompanies(Arrays.stream(CardCompany.values())
          .map(cardCompany -> GetOptionsResponse.Option.builder()
              .value(cardCompany.name())
              .label(cardCompany.getLabel())
              .build())
          .toList());
    }

    return response.build();
  }

  @PostMapping("/login")
  public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
    Login login = loginService.login(adminControllerMapper.toLogin(loginRequest), LoginType.ADMIN);

    log.debug("login: id={}, token={}", loginRequest.getId(), login.getToken());

    return adminControllerMapper.toLoginResponse(login);
  }

  @PostMapping("/logout")
  public void logout(LoginInfo loginInfo) {
    loginService.logoutAdmin(loginInfo.getToken());
  }

  @PostMapping(value = "/test/upload")
  public TestUploadResponse testUpload(TestUploadRequest request) throws IOException {
    log.info("id: {}", request.getId());
    log.info("file: {}, {}", request.getFile().getSize(), request.getFile().getOriginalFilename());

    String fileName = fileService.upload(request.getFile());

    return TestUploadResponse.builder()
        .url(fileService.getUrl(fileName, request.getFile().getOriginalFilename()))
        .build();
  }

  @GetMapping("/commonCodeGroup")
  public ListCommonCodeGroupsResponse listCommonCodeGroups() {
    List<CommonCodeGroup> commonCodeGroups = commonCodeService.listCommonCodeGroups();

    return ListCommonCodeGroupsResponse.builder()
            .commonCodeGroup(adminControllerMapper.toListCommonCodeGroupsResponse(commonCodeGroups))
            .build();
  }

  /** 공통코드 관리 페이지 목록*/
  @GetMapping("/commonCode")
  public ListCommonCodesResponse listCommonCodes(@RequestParam(value = "codeGroup", required = false) String codeGroup) {
    List<CommonCode> commonCodes = commonCodeService.listCommonCodes(codeGroup);

    return ListCommonCodesResponse.builder()
            .commonCode(adminControllerMapper.toListCommonCodesResponse(commonCodes))
            .build();
  }

  /** 공통코드 관리페이지 등록*/
  @PostMapping("/commonCode")
  public void createCommonCode(LoginInfo loginInfo, @RequestBody @Valid CreateCommonCodeRequest request) {
    CreateCommonCode createCommonCode = adminControllerMapper.toCreateCommonCode(request, loginInfo.getId());
    commonCodeService.createCommonCode(createCommonCode);
  }

  /** 공통코드 관리페이지 수정*/
  @PutMapping("/commonCode/{codeId}")
  public void updateCommonCode(LoginInfo loginInfo, @PathVariable String codeId, @RequestBody @Valid UpdateCommonCodeRequest updateCommonCodeRequest){
    UpdateCommonCode updateCommonCode = adminControllerMapper.toUpdateCommonCode(updateCommonCodeRequest, loginInfo.getId(), codeId);
    commonCodeService.updateCommonCode(updateCommonCode);

  }

  /** 공통코드 목록 조회 : 셀렉트박스 선택 시 나올 목록들
   * @request : codeGroupId : 그룹코드 식별키
   * */
  @GetMapping("/code/{codeGroupId}")
  public List<CodeList> codeList(@PathVariable String codeGroupId) {
    List<CommonCode> codes = commonCodeService.codeList(codeGroupId);
    return adminControllerMapper.toCodeList(codes);
  }

}
