package com.lms.api.admin.service;

import com.lms.api.admin.controller.dto.CalculatesResponseMapper;
import com.lms.api.admin.controller.dto.ListCalculatesResponse;
import com.lms.api.admin.service.dto.Order;
import com.lms.api.admin.service.dto.OrderProduct;
import com.lms.api.admin.service.dto.Payment;
import com.lms.api.admin.service.dto.Refund;
import com.lms.api.admin.service.dto.SearchOrders;
import com.lms.api.common.code.PaymentType;
import com.lms.api.common.code.UserType;
import com.lms.api.common.entity.OrderEntity;
import com.lms.api.common.entity.QOrderEntity;
import com.lms.api.common.entity.UserEntity;
import com.lms.api.common.repository.OrderRepository;
import com.lms.api.common.repository.PaymentRepository;
import com.lms.api.common.repository.RefundRepository;
import com.lms.api.common.repository.UserRepository;
import com.lms.api.common.util.DateUtils;
import com.lms.api.common.util.StringUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderAdminService {

  private final OrderRepository orderRepository;
  private final PaymentRepository paymentRepository;
  private final RefundRepository refundRepository;
  private final UserRepository userRepository;
  private final OrderAdminServiceMapper orderAdminServiceMapper;
  private final CalculatesResponseMapper calculatesResponseMapper;

  public ListCalculatesResponse listOrders2(SearchOrders searchOrders) {
    String dateFrom = DateUtils.getString(searchOrders.getDateFrom());
    String dateTo = DateUtils.getString(searchOrders.getDateTo());
    String creatorName = searchOrders.getCreatorName() == null ? null
        : searchOrders.getCreatorName().isBlank() ? null : searchOrders.getCreatorName();

    // summmary
    List<ListCalculatesResponse.Summary> summaries = orderRepository.listCalculateSummaryForPeriod(
            dateFrom, dateTo, creatorName)
        .stream().map(calculatesResponseMapper::toSummary)
        .toList();
    // order
    List<ListCalculatesResponse.Order> orders = orderRepository.listCalculatesOrderForPeriod(
            dateFrom, dateTo, creatorName)
        .stream().map(calculatesResponseMapper::toOrder)
        .toList();
    // payment
    List<ListCalculatesResponse.Payment> payments = orderRepository.listCalculatesPaymentForPeriod(
            dateFrom, dateTo, creatorName)
        .stream().map(calculatesResponseMapper::toPayment)
        .toList();

    // Map으로 payments를 orderId 기준으로 그룹화
    Map<String, List<ListCalculatesResponse.Payment>> paymentsMap = payments.stream()
        .collect(Collectors.groupingBy(ListCalculatesResponse.Payment::getOrderId));

    // orders 리스트를 순회하면서 해당하는 payments를 찾아서 추가
    for (ListCalculatesResponse.Order order : orders) {
      List<ListCalculatesResponse.Payment> matchingPayments = paymentsMap.get(order.getId());
      if (matchingPayments != null) {
        order.getPayments().addAll(matchingPayments);
      }
    }

    ListCalculatesResponse listCalculatesResponse = ListCalculatesResponse.builder()
        .summaries(summaries)
        .orders(orders)
        .build();

    return listCalculatesResponse;
  }

  public List<Order> listOrders(SearchOrders searchOrders) {
    QOrderEntity qOrderEntity = QOrderEntity.orderEntity;

    // 결제기간
    BooleanExpression where = qOrderEntity.paymentEntities.any().paymentDate.between(
            searchOrders.getDateFrom(), searchOrders.getDateTo())
        .or(qOrderEntity.orderProductEntities.any().refundEntities.any().refundDate.between(
            searchOrders.getDateFrom(), searchOrders.getDateTo()));
    // 처리자
    if (StringUtils.hasText(searchOrders.getCreatorName())) {
      String userId = userRepository.findFirstByNameAndType(searchOrders.getCreatorName(),
              UserType.A)
          .map(UserEntity::getId).orElse(null);

      where = where.and(qOrderEntity.paymentEntities.any().createdBy.eq(userId)
          .or(qOrderEntity.orderProductEntities.any().refundEntities.any().createdBy.eq(userId)));
    }

    Iterable<OrderEntity> orderEntities = orderRepository.findAll(where,
        qOrderEntity.createdOn.asc());

    List<Order> orders = new ArrayList<>();

    orderEntities.forEach(orderEntity -> {
      List<Payment> payments = orderEntity.getPaymentEntities().stream()
          .filter(paymentEntity -> paymentEntity.getType() == PaymentType.I || paymentEntity.getType() == PaymentType.C) // 조원빈 요청에 의해 추가
          .map(paymentEntity -> {
            String paymentCreatorName = userRepository.findById(paymentEntity.getModifiedBy())
                .map(UserEntity::getName).orElse(null);
            return orderAdminServiceMapper.toPayment(paymentEntity, paymentCreatorName);
          })
          .toList();

      List<OrderProduct> orderProducts = orderEntity.getOrderProductEntities().stream()
          .map(orderProductEntity -> {
            List<Refund> refunds = orderProductEntity.getRefundEntities().stream()
                .map(refundEntity -> {
                  String refundCreatorName = userRepository.findById(refundEntity.getModifiedBy())
                      .map(UserEntity::getName).orElse(null);
                  return orderAdminServiceMapper.toRefund(refundEntity, refundCreatorName);
                })
                .toList();

            OrderProduct orderProduct = orderAdminServiceMapper.toOrderProduct(orderProductEntity, refunds);
            // 환불 금액
            orderProduct.setRefundAmount(refunds.stream().mapToInt(Refund::getRefundAmount).sum());
            return orderProduct;
          })
          .toList();

      String orderCreatorName = userRepository.findById(orderEntity.getCreatedBy())
          .map(UserEntity::getName).orElse(null);
      Order order = orderAdminServiceMapper.toOrder(orderEntity, orderProducts, payments,
          orderCreatorName);
      order.setRefundAmount(orderProducts.stream().mapToInt(OrderProduct::getRefundAmount).sum());
      order.setReceivableAmount(order.getBillingAmount() - order.getPaymentAmount() - order.getRefundAmount());

      orders.add(order);
    });

    return orders;
  }
}
