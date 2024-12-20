package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.ListCalculatesRequest;
import com.lms.api.admin.controller.dto.ListCalculatesResponse;
import com.lms.api.admin.controller.dto.ListCalculatesResponse.Summary;
import com.lms.api.admin.service.OrderAdminService;
import com.lms.api.admin.service.dto.SearchOrders;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderAdminController {

  private final OrderAdminService orderAdminService;
  private final OrderAdminControllerMapper orderAdminControllerMapper;

  @GetMapping("/calculates")
  public ListCalculatesResponse listCalculates(@Valid ListCalculatesRequest request) {
    SearchOrders searchOrders = orderAdminControllerMapper.toSearchOrders(request);
    List<com.lms.api.admin.service.dto.Order> orders = orderAdminService.listOrders(searchOrders);
    List<ListCalculatesResponse.Order> responseOrders = orders.stream()
        .map(orderAdminControllerMapper::toOrder)
        .collect(Collectors.toList());
    List<ListCalculatesResponse.Summary> summaries = orders.stream()
        .map(order -> Summary.builder()
            .paymentAmount(order.getPaymentAmount())
            .refundAmount(order.getRefundAmount())
            .creatorName(order.getCreatorName())
            .build()).collect(Collectors.toList());

    return ListCalculatesResponse.builder()
        .summaries(summaries)
        .orders(responseOrders)
        .build();
  }
}
