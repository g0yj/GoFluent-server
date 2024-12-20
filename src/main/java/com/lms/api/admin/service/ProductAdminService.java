package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.Product;
import com.lms.api.admin.service.dto.product.CreateProduct;
import com.lms.api.admin.service.dto.product.UpdateProduct;
import com.lms.api.common.code.YN;
import com.lms.api.common.entity.ProductEntity;
import com.lms.api.common.entity.QProductEntity;
import com.lms.api.common.exception.LmsErrorCode;
import com.lms.api.common.exception.LmsException;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.repository.ProductRepository;
import com.lms.api.common.repository.UserRepository;
import com.lms.api.common.util.ListUtils;
import com.lms.api.common.util.LmsUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.querydsl.QSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductAdminService {

  private final ProductRepository productRepository;
  private final ProductAdminServiceMapper productAdminServiceMapper;
  private final ServiceMapper serviceMapper;
  private final UserRepository userRepository;

  /** 회원관리 > 주문탭 > 상품목록조회*/
  public List<Product> listProducts(Product product) {
    QProductEntity qProductEntity = QProductEntity.productEntity;

    BooleanExpression where = qProductEntity.type.eq(product.getType().getCode());

    if (product.getLanguage() != null) {
      where = where.and(qProductEntity.language.eq(product.getLanguage()));
    }

    if (product.getLessonType() != null) {
      where = where.and(qProductEntity.lessonType.eq(product.getLessonType()));
    }

    List<ProductEntity> productEntities = ListUtils.of(
        productRepository.findAll(where, QSort.by(qProductEntity.sort.asc())));

    return productAdminServiceMapper.toProducts(productEntities);
  }


  /** 상품관리 > 상품 목록 조회*/
  public List<Product> listProductManagement(){
    QProductEntity qProductEntity = QProductEntity.productEntity;

    BooleanExpression where = qProductEntity.curriculumYN.isNotNull();

    Sort sort = Sort.by(Direction.DESC,"createdOn");
    List<ProductEntity> productEntities = (List<ProductEntity>) productRepository.findAll(where,sort);
    return productEntities.stream()
        .map(productEntity ->  productAdminServiceMapper.toProduct(productEntity)).collect((Collectors.toList()));
  }

  /** 상품 등록*/
  @Transactional
  public void createProduct(CreateProduct createProduct) {
    if (createProduct.getShortCourseYN() == YN.Y && createProduct.getCurriculumYN() == YN.N) {
      throw new LmsException(LmsErrorCode.BAD_REQUEST, "과정 여부가 올바르지 않습니다.");
    }
    if(YN.Y == createProduct.getCurriculumYN()) {
        createProduct.setQuantityUnit("회");
    }
    else {
        createProduct.setQuantityUnit("개");
    }
    String id = LmsUtils.createProductId();

    ProductEntity productEntity = productAdminServiceMapper.toProductEntity(createProduct, id);
    productRepository.save(productEntity);
  }

  @Transactional
  public void updateProduct(UpdateProduct updateProduct) {
    if (updateProduct.getShortCourseYN() == YN.Y && updateProduct.getCurriculumYN() == YN.N) {
      throw new LmsException(LmsErrorCode.BAD_REQUEST, "과정 여부가 올바르지 않습니다.");
    }
    productRepository.findById(updateProduct.getId())
            .ifPresentOrElse(
                    productEntity -> {
                      if(YN.Y == updateProduct.getCurriculumYN()) {
                        updateProduct.setQuantityUnit("회");
                      }
                      else {
                        updateProduct.setQuantityUnit("개");
                      }
                      productAdminServiceMapper.mapProductEntity(updateProduct, productEntity);
                    },
                    () -> {
                      throw new LmsException(LmsErrorCode.PRODUCT_NOT_FOUND);
                    }
            );
  }

  public Product getProduct(String id){
    QProductEntity qProductEntity = QProductEntity.productEntity;
    ProductEntity productEntity = productRepository.findById(id)
        .orElseThrow(()-> new LmsException(LmsErrorCode.PRODUCT_NOT_FOUND));

    Product product = productAdminServiceMapper.toProduct(productEntity);

    return product;

  }
  @Transactional
  public void deleteProduct(String id) {productRepository.deleteById(id);}
}
