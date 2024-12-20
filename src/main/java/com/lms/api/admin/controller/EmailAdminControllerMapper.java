package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.CreateSendEmailRequest;
import com.lms.api.admin.controller.dto.CreateSendLDFEmailRequest;
import com.lms.api.admin.controller.dto.ListEmailUsersRequest;
import com.lms.api.admin.controller.dto.ListEmailUsersResponse;
import com.lms.api.admin.service.dto.CreateSendEmail;
import com.lms.api.admin.service.dto.CreateSendLDFEmail;
import com.lms.api.admin.service.dto.SearchEmailUsers;
import com.lms.api.admin.service.dto.User;
import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {
    ControllerMapper.class})
public interface EmailAdminControllerMapper {

  SearchEmailUsers toSearchEmailUsers(ListEmailUsersRequest request);

  @Mapping(target = "loginId", source = "users.loginId")
  List<ListEmailUsersResponse.User> toUsers(List<User> users);

  CreateSendEmail toCreateSendEmail(CreateSendEmailRequest request, String senderName,
      String createdBy);

  CreateSendLDFEmail toCreateSendLDFEmail(CreateSendLDFEmailRequest request, String senderName,
      String createdBy);
}
