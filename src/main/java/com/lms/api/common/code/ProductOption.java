package com.lms.api.common.code;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ProductOption {
  SILVER("색상:Silver"),
  WHITE("색상:White"),
  BLACK("색상:Black")
  ;

  String label;

  public static List<ProductOption> getProductOption() {return List.of(SILVER, WHITE, BLACK);}

  public static ProductOption of(String label) {
    return Arrays.stream(values())
        .filter(value -> value.getLabel().equals(label))
        .findFirst()
        .orElse(null);
  }

  public static String to(ProductOption productOption) {
    return productOption == null ? null : productOption.getLabel();
  }

  public static String toOptionString(List<ProductOption> options) {
    if (options == null || options.isEmpty()) {
      return "";
    }
    return options.stream()
        .map(ProductOption::getLabel)
        .collect(Collectors.joining(","));
  }

  public static String labelToEnumName(String label) {
    ProductOption option = of(label);
    return option != null ? option.name() : null;
  }
}
