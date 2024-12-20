package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.CreateCommonCodeRequest;
import com.lms.api.admin.controller.dto.GetOptionsResponse.Option;
import com.lms.api.admin.controller.dto.ListCommonCodeGroupsResponse;
import com.lms.api.admin.controller.dto.ListCommonCodesResponse;
import com.lms.api.admin.controller.dto.UpdateCommonCodeRequest;
import com.lms.api.admin.controller.dto.common.CodeList;
import com.lms.api.admin.service.dto.CommonCode;
import com.lms.api.admin.service.dto.CommonCodeGroup;
import com.lms.api.admin.service.dto.CreateCommonCode;
import com.lms.api.admin.service.dto.Login;
import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.admin.service.dto.UpdateCommonCode;
import com.lms.api.admin.service.dto.User;
import com.lms.api.common.controller.dto.LoginRequest;
import com.lms.api.common.controller.dto.LoginResponse;
import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {
    ControllerMapper.class})
public interface AdminControllerMapper {

  @Mapping(target = "value", source = "userId")
  @Mapping(target = "label", source = "user.name")
  Option toOption(Teacher teacher);

  @Mapping(target = "value", source = "id")
  @Mapping(target = "label", source = "name")
  Option toOption(User user);

  @Mapping(target = "loginId", source = "id")
  Login toLogin(LoginRequest loginRequest);

  LoginResponse toLoginResponse(Login login);

  List<ListCommonCodeGroupsResponse.CommonCodeGroup> toListCommonCodeGroupsResponse(
          List<CommonCodeGroup> responseCommonCodeGroups);

  List<ListCommonCodesResponse.CommonCode> toListCommonCodesResponse(
          List<CommonCode> commonCodes);

  @Mapping(target = "createdBy", source = "createdBy")
  @Mapping(target = "useYn", source = "request.useYn")
  @Mapping(target = "code", source = "request.code")
  CreateCommonCode toCreateCommonCode(CreateCommonCodeRequest request, String createdBy);

  @Mapping(target = "modifiedBy", source = "modifiedBy")
  @Mapping(target = "code", source = "codeId")
  @Mapping(target = "codeGroup", source = "request.codeGroup")
  UpdateCommonCode toUpdateCommonCode(UpdateCommonCodeRequest request, String modifiedBy, String codeId);

  List<CodeList> toCodeList(List<CommonCode> codes);
}
