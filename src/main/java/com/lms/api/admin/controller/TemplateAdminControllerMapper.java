package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.template.CreateTemplateRequest;
import com.lms.api.admin.controller.dto.template.ListReportExcelResponse;
import com.lms.api.admin.controller.dto.template.ListSmsExcelResponse;
import com.lms.api.admin.controller.dto.template.ListUsersExcelResponse;
import com.lms.api.admin.controller.dto.template.UpdateTemplateRequest;
import com.lms.api.admin.service.dto.Reservation;
import com.lms.api.admin.service.dto.User;
import com.lms.api.admin.service.dto.statistics.Sms;
import com.lms.api.admin.service.dto.template.CreateTemplate;
import com.lms.api.admin.service.dto.template.UpdateTemplate;
import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {
    ControllerMapper.class})
public interface TemplateAdminControllerMapper {


  /** 템플릿 등록*/
  CreateTemplate toCreateTemplate(CreateTemplateRequest request,
      String loginId);


  /** 템플릿 수정*/
  @Mapping(target = "id" , source = "id")
  UpdateTemplate toUpdateTemplate(UpdateTemplateRequest request, long id, String modifiedBy);

  List<ListReportExcelResponse> toListReportExcelResponse(List<Reservation> listReport);

  ListReportExcelResponse toListReportExcelResponse(Reservation reservation);

  List<ListUsersExcelResponse>toListUserExcelResponse(List<User> users);

  ListUsersExcelResponse toListUserExcelResponse(User user);

  ListSmsExcelResponse toListSmsExcelResponse(Sms sms);
}