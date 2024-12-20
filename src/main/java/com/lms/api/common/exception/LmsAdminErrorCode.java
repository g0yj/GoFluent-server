//package com.lms.api.common.exception;
//
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//
//@Getter
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public enum LmsAdminErrorCode implements LmsError {
//    // common error
//    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "9999", "서버에 문제가 발생했습니다."),
//    BAD_REQUEST(HttpStatus.BAD_REQUEST, "9900", "잘못된 요청입니다."),
//
//    // auth error
//    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, "0100", "로그인이 필요합니다."),
//
//    // user error
//    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "0200", "사용자를 찾을 수 없습니다."),
//
//    // schedule error
//    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "0300", "스케줄을 찾을 수 없습니다."),
//
//    // course error
//    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "0400", "강의를 찾을 수 없습니다."),
//    LESSON_COUNT_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "0401", "수업 횟수가 부족합니다."),
//
//    // teacher error
//    TEACHER_NOT_FOUND(HttpStatus.NOT_FOUND, "0500", "강사를 찾을 수 없습니다."),
//
//    // reservation error
//    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "0600", "예약을 찾을 수 없습니다."),
//
//    // order error
//    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "0700", "주문을 찾을 수 없습니다."),
//    BILLING_AMOUNT_NOT_MATCH(HttpStatus.BAD_REQUEST, "0701", "금액이 일치하지 않습니다."),
//
//    // product error
//    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "0800", "상품을 찾을 수 없습니다."),
//
//    // ldf error
//    LDF_NOT_FOUND(HttpStatus.NOT_FOUND, "0900" , "Ldf를 찾을 수 없습니다"),
//
//    // consultation error
//    CONSULTATION_NOT_FOUND(HttpStatus.NOT_FOUND,"1000","상담 내역을 찾을 수 없습니다"),
//    // valCellPhone error
//    CELLPHONE_NOT_MATCH(HttpStatus.BAD_REQUEST ,"1001","중복되는 번호가 있습니다" ),
//
//    // course_history error ( = note error)
//    NOTE_NOT_FOUND(HttpStatus.NOT_FOUND,"1100","비고 내역을 찾을 수 없습니다"),
//
//    // levelTest error
//    LEVELTEST_NOT_FOUND(HttpStatus.NOT_FOUND, "1200" , "레벨테스트를 찾을 수 없습니다"),
//
//    // sms error
//    SMS_NOT_FOUND(HttpStatus.NOT_FOUND,"1300","SMS를 찾을 수 없습니다"),
//
//
//    // loginId error
//    LOGIN_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "1400", "동일한 ID가 존재합니다"),
//
//
//    ;
//
//    HttpStatusCode httpStatusCode;
//    String code;
//    String message;
//
//    public String getCode() {
//        return "admin-api-" + code;
//    }
//}
