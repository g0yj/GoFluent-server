package com.lms.api.admin.controller.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListProductsResponse {

  List<Product> products;

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Product {

    String id;
    String name;
    int price;
    List<Option> options;
  }

  @Getter
  @Builder
  @FieldDefaults(level = AccessLevel.PRIVATE)
  public static class Option {

    String value;
    String label;
  }
}
