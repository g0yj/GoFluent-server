package com.lms.api.client.sms.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * 메시지 발송 결과 조회 응답
 */
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListSmsSendRequestResponse {

  public static final String SUCCESS_STATUS_CODE = "202";

  /**
   * 202: 성공 그 외: 실패
   */
  String statusCode;
  /**
   * success: 성공 reserved: 예약 중 fail: 실패
   */
  StatusName statusName;
  /**
   * 페이지 인덱스
   */
  int pageIndex;
  /**
   * 페이지 사이즈
   */
  int pageSize;
  /**
   * 조회한 페이지 내의 메시지 수
   */
  int itemCount;
  /**
   * 다음 페이지 존재 여부
   */
  boolean hasMore;

  List<Message> messages = new ArrayList<>();

  /**
   * 단말기 수신 성공 여부
   */
  public boolean isSuccess() {
    if (messages != null && !messages.isEmpty()) {
      return messages.stream().allMatch(message -> message.isSuccess());
    }
    return false;
  }

  /**
   * 단말기 수신 실패
   */
  public boolean isFail() {
    if (messages != null && !messages.isEmpty()) {
      return messages.stream().anyMatch(message -> message.isFailed());
    }
    return false;
  }

  @Getter
  @ToString
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Message {

    public static final String SUCCESS_STATUS_CODE = "0";

    /**
     * 메시지 요청 아이디
     */
    String requestId;
    /**
     * 메시지 아이디
     */
    String messageId;
    /**
     * 발송 요청 시간
     */
    String requestTime;
    /**
     * 메시지 Type
     */
    String contentType;
    /**
     * 국가 번호
     */
    String countryCode;
    /**
     * 발신번호
     */
    String from;
    /**
     * 수신번호
     */
    String to;
    /**
     * 발송 요청 상태
     */
    Status status;
    /**
     * 단말 수신 상태 결과 코드
     */
    String statusCode;
    /**
     * 단말 수신 상태 결과명
     */
    StatusName statusName;
    /**
     * 단말 수신 상태 결과 메시지
     */
    String statusMessage;
    /**
     * 발송 완료 시간
     */
    String completeTime;
    /**
     * 통신사코드
     */
    String telcoCode;

    public boolean isSuccess() {
      if (status != null && statusName != null) {
        return status == Status.COMPLETED && statusName == StatusName.success;
      }
      return false;
    }

    public boolean isFailed() {
      if (status != null && statusName != null) {
        return status == Status.COMPLETED && statusName == StatusName.fail;
      }
      return false;
    }
  }
}
