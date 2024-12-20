package com.lms.api.mobile.service.dto;

import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePassword {
    String id;

    String password;
    String newPassword;
    String checkPassword;

    String modifiedBy;

    public void validatePasswordPattern() {
        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,14}$";
        if (!this.newPassword.matches(pattern) || !this.checkPassword.matches(pattern)) {
            throw new LmsException(LmsErrorCode.CHANGEPW4_SERVER_ERROR);
        }
    }
}
