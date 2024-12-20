package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.ListTeacherMemberResponse;
import com.lms.api.admin.controller.dto.ListTeacherMembersRequest;
import com.lms.api.admin.controller.dto.teacher.ListTeacherResponse;
import com.lms.api.admin.service.TeacherService;
import com.lms.api.admin.service.dto.SearchTeacherUsers;
import com.lms.api.admin.service.dto.Teacher;
import com.lms.api.admin.service.dto.TeacherPageUserList;
import com.lms.api.common.controller.dto.PageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 강사 계정 */
@RestController
@RequestMapping("/teacher/v1")
@RequiredArgsConstructor
public class TeacherController {

  private final TeacherService teacherService;
  private final TeacherControllerMapper teacherControllerMapper;

  /** 회원 목록 조회 */
  @GetMapping("/users")
  public PageResponse<ListTeacherMemberResponse> listUsers(ListTeacherMembersRequest request){
    SearchTeacherUsers users = teacherControllerMapper.toSearchTeacherUser(request);
    Page<TeacherPageUserList> userPage = teacherService.listUsers(users);
    return teacherControllerMapper.toListTeacherMemberResponse(userPage, users);

  }

  /** 강사목록조회(영문명포함)*/
  @GetMapping("/teachers")
  public List<ListTeacherResponse> listTeachers(){
    List<Teacher> teachers = teacherService.listTeachers();
    List<ListTeacherResponse> listTeacher = teacherControllerMapper.toListTeacherResponse(teachers);
    return  listTeacher;
  }


}
