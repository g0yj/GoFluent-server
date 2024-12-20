package com.lms.api.client.holiday.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HolidayResponse {

  Response response;

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  @ToString
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Response {
    Header header;
    Body body;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @ToString
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Header {
    String resultCode;
    String resultMsg;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  @ToString
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Body {
    @JsonDeserialize(using = ItemsDeserializer.class) // 커스텀 deserializer 적용
    Items items;
    Integer numOfRows;
    Integer pageNo;
    Integer totalCount;
  }

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  @ToString
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Items {
    @JsonProperty("item")
    List<Item> itemList;
  }

  @Getter
  @AllArgsConstructor
  @NoArgsConstructor
  @ToString
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Item {
    String dateKind;
    String dateName;
    String isHoliday;
    Integer locdate;
    Integer seq;
  }
}
