package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.CreateSendEmailRequest;
import com.lms.api.admin.controller.dto.CreateSendLDFEmailRequest;
import com.lms.api.admin.controller.dto.ListEmailUsersRequest;
import com.lms.api.admin.controller.dto.ListEmailUsersResponse;
import com.lms.api.admin.service.EmailAdminService;
import com.lms.api.admin.service.dto.SearchEmailUsers;
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
@RequestMapping("/admin/v1/email")
@RequiredArgsConstructor
public class EmailAdminController {

  private final EmailAdminService emailAdminService;
  private final EmailAdminControllerMapper emailAdminControllerMapper;

  @GetMapping("/users")
  public ListEmailUsersResponse listUsers(ListEmailUsersRequest request) {
    SearchEmailUsers searchEmailUsers = emailAdminControllerMapper.toSearchEmailUsers(request);
    List<User> users = emailAdminService.listUsers(searchEmailUsers);

    return ListEmailUsersResponse.builder()
        .users(emailAdminControllerMapper.toUsers(users))
        .build();
  }

  @PostMapping("/send")
  public void send(LoginInfo loginInfo, @RequestBody @Valid CreateSendEmailRequest request) {
    emailAdminService.createSendEmail(
        emailAdminControllerMapper.toCreateSendEmail(request, loginInfo.getName(),
            loginInfo.getId()));
  }

  @PostMapping("/ldf/send")
  public void sendLDF(LoginInfo loginInfo, @RequestBody @Valid CreateSendLDFEmailRequest request) {
    emailAdminService.createSendLDFEmail(
        emailAdminControllerMapper.toCreateSendLDFEmail(
            request, loginInfo.getName(), loginInfo.getId()));
  }
}

