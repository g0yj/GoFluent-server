package com.lms.api.mobile.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderProduct {
  String id;
  Product product;

  public String getProductName() {
    return product == null ? null : product.getName();
  }
}
