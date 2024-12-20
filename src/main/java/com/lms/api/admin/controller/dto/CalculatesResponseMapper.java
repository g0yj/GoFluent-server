package com.lms.api.admin.controller.dto;

import com.lms.api.common.dto.Order;
import com.lms.api.common.dto.Payment;
import com.lms.api.common.dto.Summary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CalculatesResponseMapper {

  ListCalculatesResponse.Summary toSummary(Summary summary);

//  @Mapping(target = "userId", ignore = true)
  @Mapping(target = "payments", ignore = true)
  ListCalculatesResponse.Order toOrder(Order order);

  ListCalculatesResponse.Payment toPayment(Payment payment);
}
