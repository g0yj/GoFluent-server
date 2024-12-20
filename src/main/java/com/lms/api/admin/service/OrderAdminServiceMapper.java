package com.lms.api.admin.service;

import com.lms.api.admin.service.dto.Order;
import com.lms.api.admin.service.dto.OrderProduct;
import com.lms.api.admin.service.dto.Payment;
import com.lms.api.admin.service.dto.Refund;
import com.lms.api.common.entity.OrderEntity;
import com.lms.api.common.entity.OrderProductEntity;
import com.lms.api.common.entity.PaymentEntity;
import com.lms.api.common.entity.RefundEntity;
import com.lms.api.common.mapper.ServiceMapper;
import com.lms.api.common.mapper.ServiceMapperConfig;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", config = ServiceMapperConfig.class, uses = {ServiceMapper.class})
public interface OrderAdminServiceMapper {

  Payment toPayment(PaymentEntity paymentEntity, String creatorName);

  Refund toRefund(RefundEntity refundEntity, String creatorName);

  @Mapping(target = "product", source = "orderProductEntity.productEntity")
  OrderProduct toOrderProduct(OrderProductEntity orderProductEntity, List<Refund> refunds);

  @Mapping(target = "user", source = "orderEntity.userEntity")
  Order toOrder(OrderEntity orderEntity, List<OrderProduct> orderProducts, List<Payment> payments,
      String creatorName);
}
