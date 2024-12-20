package com.lms.api.common.config;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import com.lms.api.common.code.UserType;
import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import com.lms.api.common.service.LoginService;
import com.lms.api.common.util.Consts;
import com.lms.api.common.util.ObjectUtils;
import com.lms.api.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginAdminInterceptor implements HandlerInterceptor {

  private final LoginService loginService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    if (request.getMethod().equals("OPTIONS")) {
      return true;
    }

    // 특정 헤더가 있으면 패스 (테스트용)
    String profile = request.getHeader(Consts.TEST_HEADER_NAME);

    if (ObjectUtils.equals(profile, Consts.TEST_HEADER_VALUE)) {
      request.setAttribute("userType", UserType.A);
      return true;
    } else if (ObjectUtils.equals(profile, Consts.TEST_HEADER_VALUE_TEACHER)) {
      request.setAttribute("userType", UserType.T);
      return true;
    }

    String token = request.getHeader(AUTHORIZATION);

    if (StringUtils.hasNotText(token)) {
      throw new LmsException(LmsErrorCode.LOGIN_REQUIRED);
    }

    loginService.getLoginInfo(token);

    return true;
  }
}
