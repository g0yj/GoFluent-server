package com.lms.api.mobile.controller.dto;

import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePasswordRequest {
    String password;
    String newPassword;
    String checkPassword;

    public void validatePasswordPattern() {
        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,14}$";
        if (!this.newPassword.matches(pattern) || !this.checkPassword.matches(pattern)) {
            throw new LmsException(LmsErrorCode.CHANGEPW4_SERVER_ERROR);
        }
    }
}
