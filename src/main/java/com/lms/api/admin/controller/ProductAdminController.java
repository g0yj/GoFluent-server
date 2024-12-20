package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.ListProductLanguagesResponse;
import com.lms.api.admin.controller.dto.ListProductLessonTypesResponse;
import com.lms.api.admin.controller.dto.ListProductTypesResponse;
import com.lms.api.admin.controller.dto.ListProductsRequest;
import com.lms.api.admin.controller.dto.ListProductsResponse;
import com.lms.api.admin.controller.dto.product.CreateProductRequest;
import com.lms.api.admin.controller.dto.product.GetProductResponse;
import com.lms.api.admin.controller.dto.product.ListManagementProductResponse;
import com.lms.api.admin.controller.dto.product.UpdateProductRequest;
import com.lms.api.admin.service.ProductAdminService;
import com.lms.api.admin.service.dto.Product;
import com.lms.api.admin.service.dto.product.CreateProduct;
import com.lms.api.admin.service.dto.product.UpdateProduct;
import com.lms.api.common.code.LanguageCode;
import com.lms.api.common.code.LessonType;
import com.lms.api.common.code.ProductType;
import com.lms.api.common.dto.LoginInfo;
import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/products")
@RequiredArgsConstructor
public class ProductAdminController {

  private final ProductAdminService productAdminService;
  private final ProductAdminControllerMapper productAdminControllerMapper;

  @GetMapping("/types")
  public ListProductTypesResponse listProductTypes() {
    return ListProductTypesResponse.builder()
        .types(productAdminControllerMapper.toProductTypes(Arrays.asList(ProductType.values())))
        .build();
  }

  @GetMapping("/languages")
  public ListProductLanguagesResponse listProductLanguages() {
    return ListProductLanguagesResponse.builder()
        .languages(productAdminControllerMapper.toLanguages(LanguageCode.getProductLanguages()))
        .build();
  }

  @GetMapping("/lessonTypes")
  public ListProductLessonTypesResponse listProductLessonTypes() {
    return ListProductLessonTypesResponse.builder()
        .lessonTypes(productAdminControllerMapper.toLessonTypes(Arrays.asList(LessonType.values())))
        .build();
  }

  @GetMapping
  public ListProductsResponse listProducts(@Valid ListProductsRequest request) {
    Product product = productAdminControllerMapper.toProduct(request);

    List<Product> products = productAdminService.listProducts(product);

    return ListProductsResponse.builder()
        .products(productAdminControllerMapper.toListProductsResponse(products))
        .build();
  }


  /** 상품관리 > 상품 목록 조회*/
  @GetMapping("/list")
  public List<ListManagementProductResponse> listProductManagement(){
    List<Product> products = productAdminService.listProductManagement();
    return productAdminControllerMapper.toListManagementProductResponse(products);
  }
  /** 상품 등록*/
  @PostMapping
  public void createProduct(LoginInfo loginInfo, @Valid @RequestBody CreateProductRequest request) {
    CreateProduct createProduct = productAdminControllerMapper.toCreateProduct(request,loginInfo.getId());
    productAdminService.createProduct(createProduct);
  }

  /** 상품 수정*/
  @PutMapping("/{id}")
  public void updateProduct(@PathVariable String id, LoginInfo loginInfo, @Valid @RequestBody UpdateProductRequest request){
    UpdateProduct updateProduct = productAdminControllerMapper.toUpdateProduct(request , loginInfo.getId() , id);
    productAdminService.updateProduct(updateProduct);
  }

  /** 상품 조회*/
  @GetMapping("/{id}")
  public GetProductResponse getProduct(@PathVariable String id) {
    Product product = productAdminService.getProduct(id);
    return productAdminControllerMapper.toGetProductResponse(product);
  }

  /** 상품 삭제*/
  @DeleteMapping("/{id}")
  public void deleteProduct(@PathVariable String id){
    productAdminService.deleteProduct(id);
  }
}
