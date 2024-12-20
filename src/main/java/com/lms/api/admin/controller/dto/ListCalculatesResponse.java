package com.lms.api.admin.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListCalculatesResponse {

  List<Summary> summaries;
  List<Order> orders;

  @Getter
  @Builder
  @ToString
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Summary {

    String creatorName;
    int paymentAmount;
    int refundAmount;
  }

  @Getter
  @Builder
  @ToString
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Order {

    String id;
    int billingAmount;
    int paymentAmount;
    int refundAmount;
    int receivableAmount;
    String creatorName;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdOn;

    String orderProductName;
    String userId;
    String userName;

    @Builder.Default
    List<Payment> payments = new ArrayList<>();
  }

  @Getter
  @Builder
  @ToString
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Payment {

    String orderId;
    int paymentAmount;
    int refundAmount;
    String transactionName;
    Integer installmentMonths;
    String cardNumber;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdOn;

    String creatorName;

    public String getOrderType() {
      if (refundAmount > 0) {
        return "환불";
      }

      if (paymentAmount < 0) {
        return "취소";
      }

      return "신규";
    }
  }
}
