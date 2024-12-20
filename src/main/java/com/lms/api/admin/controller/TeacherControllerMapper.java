package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.ListTeacherMemberResponse;
import com.lms.api.admin.controller.dto.ListTeacherMembersRequest;
import com.lms.api.admin.controller.dto.teacher.ListTeacherResponse;
import com.lms.api.admin.service.dto.SearchTeacherUsers;
import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.admin.service.dto.TeacherPageUserList;
import com.lms.api.common.controller.dto.PageResponse;
import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import com.lms.api.common.service.dto.Search;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

/** 강사 계정 */
@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {ControllerMapper.class})
public interface TeacherControllerMapper {
  @Mapping(source = "today", target = "today")
  SearchTeacherUsers toSearchTeacherUser(ListTeacherMembersRequest request);

  @Mapping(target = "list", source = "userPage.content")
  @Mapping(target = "totalCount", source = "userPage.totalElements")
  PageResponse<ListTeacherMemberResponse> toListTeacherMemberResponse(Page<TeacherPageUserList> userPage, Search search);

  @Mapping(target = "email" , source = "email")
  ListTeacherMemberResponse toListTeacherMemberResponse(TeacherPageUserList teacherPageUserList);

  List<ListTeacherResponse> toListTeacherResponse(List<Teacher> teachers);

   @Mapping(target = "userId" , source = "user.id")
   @Mapping(target = "type" , source = "type")
   @Mapping(target = "teacherName" , source = "user.name")
   @Mapping(target = "teacherEnName" , source = "user.nameEn")
   @Mapping(target = "email" , source = "user.email")
   @Mapping(target = "cellPhone" , source = "user.cellPhone")
   @Mapping(target = "sort" , source = "sort")
   @Mapping(target = "active" , source = "active")
  ListTeacherResponse toListTeacherResponse(Teacher teacher);
}
