package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.ListProductsRequest;
import com.lms.api.admin.controller.dto.ListProductsResponse;
import com.lms.api.admin.controller.dto.common.Code;
import com.lms.api.admin.controller.dto.product.CreateProductRequest;
import com.lms.api.admin.controller.dto.product.GetProductResponse;
import com.lms.api.admin.controller.dto.product.ListManagementProductResponse;
import com.lms.api.admin.controller.dto.product.UpdateProductRequest;
import com.lms.api.admin.service.dto.Product;
import com.lms.api.admin.service.dto.product.CreateProduct;
import com.lms.api.admin.service.dto.product.UpdateProduct;
import com.lms.api.common.code.LanguageCode;
import com.lms.api.common.code.LessonType;
import com.lms.api.common.code.ProductType;
import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {
    ControllerMapper.class})
public interface ProductAdminControllerMapper {

  List<Code> toProductTypes(List<ProductType> productTypes);

  default Code toProductType(ProductType productType) {
    return Code.builder()
        .value(productType.name())
        .label(productType.getLabel())
        .build();
  }

  List<Code> toLanguages(List<LanguageCode> languages);

  default Code toLanguage(LanguageCode language) {
    return Code.builder()
        .value(language.name())
        .label(language.getLabel())
        .build();
  }

  List<Code> toLessonTypes(List<LessonType> lessonTypes);

  default Code toLessonType(LessonType lessonType) {
    return Code.builder()
        .value(lessonType.name())
        .label(lessonType.name())
        .build();
  }

  Product toProduct(ListProductsRequest request);

  List<ListProductsResponse.Product> toListProductsResponse(List<Product> products);

  List<ListManagementProductResponse> toListManagementProductResponse(List<Product> product);

  @Mapping(target = "createdBy" , source = "loginId" )
  CreateProduct toCreateProduct(CreateProductRequest request , String loginId);

  @Mapping(target = "modifiedBy" , source = "loginId" )
  @Mapping(target = "id" , source = "id" )
  UpdateProduct toUpdateProduct(UpdateProductRequest request , String loginId , String id);

  GetProductResponse toGetProductResponse(Product product);

}
