package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.Product;
import com.lms.api.admin.service.dto.product.CreateProduct;
import com.lms.api.admin.service.dto.product.UpdateProduct;
import com.lms.api.common.code.ProductOption;
import com.lms.api.common.entity.ProductEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {ServiceMapper.class})
public interface ProductAdminServiceMapper {

  List<Product> toProducts(List<ProductEntity> productEntities);

 // List<GetProduct> toGetProduct(List<ProductEntity> productEntities);

  List<Product> toProduct(List<ProductEntity> productEntity);



  @Mapping(target = "id" , source = "id")
  @Mapping(target = "quantityUnit" , source = "createProduct.quantityUnit")
  ProductEntity toProductEntity(CreateProduct createProduct , String id );

  @Mapping(target = "id" , source = "id")
  ProductEntity toProductEntity(UpdateProduct updateProduct , String options, String id , String typeCode);

  @Mapping(target = "id" , ignore = true)
  void mapProductEntity(UpdateProduct updateProduct, @MappingTarget ProductEntity productEntity);

  default String map(List<ProductOption> value) {
    return value != null ? value.stream()
        .map(ProductOption::toString)
        .collect(Collectors.joining(",")) : null;
  }

  @Mapping(target = "sort" , source = "productEntity.sort")
  Product toProduct(ProductEntity productEntity);


}
