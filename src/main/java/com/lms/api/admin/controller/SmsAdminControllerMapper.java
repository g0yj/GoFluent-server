package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.CreateSendSmsRequest;
import com.lms.api.admin.controller.dto.ListSmsUsersRequest;
import com.lms.api.admin.controller.dto.ListSmsUsersResponse;
import com.lms.api.admin.service.dto.CreateSendSms;
import com.lms.api.admin.service.dto.SearchSmsUsers;
import com.lms.api.admin.service.dto.User;
import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {
    ControllerMapper.class})
public interface SmsAdminControllerMapper {

  SearchSmsUsers toSearchSmsUsers(ListSmsUsersRequest request);

  List<ListSmsUsersResponse.User> toUsers(List<User> users);

  CreateSendSms toCreateSendSms(CreateSendSmsRequest request, String senderName, String createdBy);
}
