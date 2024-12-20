package com.lms.api.admin.controller;

import com.lms.api.admin.controller.dto.ListCalculatesRequest;
import com.lms.api.admin.controller.dto.ListCalculatesResponse;
import com.lms.api.admin.service.dto.SearchOrders;
import com.lms.api.common.mapper.ControllerMapper;
import com.lms.api.common.mapper.ControllerMapperConfig;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = ControllerMapperConfig.class, uses = {
    ControllerMapper.class})
public interface OrderAdminControllerMapper {

  SearchOrders toSearchOrders(ListCalculatesRequest request);

  List<ListCalculatesResponse.Order> toOrders(List<com.lms.api.admin.service.dto.Order> orders);

  ListCalculatesResponse.Order toOrder(com.lms.api.admin.service.dto.Order order);

  List<ListCalculatesResponse.Payment> toPayments(List<com.lms.api.admin.service.dto.Payment> payments);

  ListCalculatesResponse.Payment toPayment(com.lms.api.admin.service.dto.Payment payment);

}
