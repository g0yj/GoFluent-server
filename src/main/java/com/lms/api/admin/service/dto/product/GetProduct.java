package com.lms.api.admin.service.dto.product;

import com.lms.api.admin.service.dto.Product;
import com.lms.api.common.code.ProductOption;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetProduct {

  Product product;
  List<ProductOption> options;

}
