package com.lms.api.mobile.controller;

import com.lms.api.admin.service.dto.Login;
import com.lms.api.common.code.LoginType;
import com.lms.api.common.code.UserType;
import com.lms.api.common.controller.dto.LoginRequest;
import com.lms.api.common.controller.dto.LoginResponse;
import com.lms.api.common.dto.LoginInfo;
import com.lms.api.common.service.LoginService;
import com.lms.api.mobile.controller.dto.ListMainReservationResponse;
import com.lms.api.mobile.controller.dto.MobileMainResponse;
import com.lms.api.mobile.controller.dto.UpdatePasswordRequest;
import com.lms.api.mobile.controller.dto.UpdatePasswordResponse;
import com.lms.api.mobile.service.CourseService;
import com.lms.api.mobile.service.ReservationService;
import com.lms.api.mobile.service.UserStudentService;
import com.lms.api.mobile.service.dto.Course;
import com.lms.api.mobile.service.dto.Reservation;
import com.lms.api.mobile.service.dto.UpdatePassword;
import com.lms.api.mobile.service.dto.UserInfo;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobile/v1")
@RequiredArgsConstructor
@Slf4j
public class MobileController {
  private final LoginService loginService;
  private final CourseService courseService;
  private final MobileControllerMapper mobileControllerMapper;
  private final UserStudentService userStudentService;
  private final ReservationService reservationService;

  @PostMapping("/login")
  public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
    Login login = loginService.login(mobileControllerMapper.toLogin(loginRequest, UserType.S), LoginType.USER);

    log.debug("login: id={}, token={}", loginRequest.getId(), login.getToken());

    return mobileControllerMapper.toLoginResponse(login);
  }

  @PostMapping("/logout")
  public void logout(LoginInfo loginInfo) {
    loginService.logoutAdmin(loginInfo.getToken());
  }

  @GetMapping("/main")
  public MobileMainResponse main(LoginInfo loginInfo) {
    List<Course> courses = courseService.listCourses(loginInfo.getId());

    return MobileMainResponse.builder()
        .courses(mobileControllerMapper.toCourses(courses))
        .build();
  }

  @GetMapping("/main/reservations")
  public List<ListMainReservationResponse> listMainReservation(LoginInfo loginInfo) {
    List<Reservation> reservations = reservationService.listMainReservation(loginInfo.getId());
    return mobileControllerMapper.toListMainReservationResponse(reservations);
  }

  /** 비밀번호 변경*/
  @PutMapping("/password")
  public UpdatePasswordResponse UpdatePasswordRequest(LoginInfo loginInfo, @Valid @RequestBody UpdatePasswordRequest request){
    UpdatePassword password = mobileControllerMapper.toUpdatePassword(loginInfo.getId() , request);
    return userStudentService.updatePassword(password);
  }

  @GetMapping("/myinfo")
  public UserInfo getMyInfo(LoginInfo loginInfo) {
    return userStudentService.getMyInfo(loginInfo.getId());
  }
}
