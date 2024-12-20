package com.lms.api.common.service;

import com.lms.api.admin.service.dto.Login;
import com.lms.api.common.code.LoginType;
import com.lms.api.common.dto.LoginInfo;
import com.lms.api.common.entity.UserLoginEntity;
import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import com.lms.api.common.repository.UserLoginRepository;
import com.lms.api.common.repository.UserRepository;
import com.lms.api.common.util.LmsUtils;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

  private final UserRepository userRepository;
  private final UserLoginRepository userLoginRepository;

  @Transactional
  public Login login(Login login, LoginType loginType) {
    log.debug("## login: {}", login.toString());
    return userRepository.findByLoginId(login.getLoginId()).map(user -> {
      log.debug("## user: {}", user);
      // 사용자가 강사고 요청 사용자 타입과 사용자의 타입이 다르면
      if (user.getType() != login.getType()) {
        throw new LmsException(LmsErrorCode.ACCESS_DENIED);
      }
      // 사용자가 활성이 아니면
      if (!user.isActive()) {
        throw new LmsException(LmsErrorCode.USER_INACTIVE);
      }
      // 패스워드가 다르면
      if (!LmsUtils.checkPassword(login.getPassword(), user.getPassword())) {
        throw new LmsException(LmsErrorCode.PASSWORD_MISMATCH);
      }

      UserLoginEntity userLoginEntity = new UserLoginEntity();
      userLoginEntity.setToken(UUID.randomUUID().toString());
      userLoginEntity.setUserEntity(user);
      userLoginEntity.setLoginType(loginType);
      userLoginEntity.setCreatedBy(user.getId());
      // 로그인 이력 저장
      userLoginRepository.save(userLoginEntity);

      return Login.builder()
          .id(user.getId())
          .name(user.getName())
          .token(userLoginEntity.getToken())
          .build();
    }).orElseThrow(() -> new LmsException(LmsErrorCode.ID_NOT_EXIST));
  }

  @Transactional
  public void logoutAdmin(String token) {
    userLoginRepository.findByToken(token)
        .ifPresent(userLoginEntity -> userLoginRepository.deleteAllByUserEntity(userLoginEntity.getUserEntity()));
  }

  @Transactional(readOnly = true)
  public LoginInfo getLoginInfo(String token) {
    return userLoginRepository.findByToken(token)
        .map(login -> LoginInfo.builder()
            .id(login.getUserEntity().getId())
            .name(login.getUserEntity().getName())
            .token(login.getToken())
            .type(login.getUserEntity().getType())
            .build())
        .orElseThrow(() -> new LmsException(LmsErrorCode.LOGIN_REQUIRED));
  }
}
