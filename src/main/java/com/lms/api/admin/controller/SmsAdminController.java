package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.CreateSendSmsRequest;
import com.lms.api.admin.controller.dto.CreateSendSmsResponse;
import com.lms.api.admin.controller.dto.ListSmsUsersRequest;
import com.lms.api.admin.controller.dto.ListSmsUsersResponse;
import com.lms.api.admin.service.SmsAdminService;
import com.lms.api.admin.service.dto.SearchSmsUsers;
import com.lms.api.admin.service.dto.User;
import com.lms.api.common.dto.LoginInfo;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/sms")
@RequiredArgsConstructor
public class SmsAdminController {

  private final SmsAdminService smsAdminService;
  private final SmsAdminControllerMapper smsAdminControllerMapper;

  @GetMapping("/users")
  public ListSmsUsersResponse listUsers(ListSmsUsersRequest request) {
    SearchSmsUsers searchSmsUsers = smsAdminControllerMapper.toSearchSmsUsers(request);
    List<User> users = smsAdminService.listUsers(searchSmsUsers);

    return ListSmsUsersResponse.builder()
        .users(smsAdminControllerMapper.toUsers(users))
        .build();
  }

  @PostMapping("/send")
  public CreateSendSmsResponse send(LoginInfo loginInfo, @RequestBody @Valid CreateSendSmsRequest request) {
    return smsAdminService.createSendSms(
        smsAdminControllerMapper.toCreateSendSms(request, loginInfo.getName(),
            loginInfo.getId()));
  }
}

