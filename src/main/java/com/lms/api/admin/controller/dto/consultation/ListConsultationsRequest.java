package com.lms.api.admin.controller.dto.consultation;

import com.lms.api.common.controller.dto.PageRequest;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;


@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListConsultationsRequest extends PageRequest {

  LocalDate createDateFrom; // 등록일 (상담일이랑다름)
  LocalDate createDateTo;


  LocalDate visitDateFrom; // 방문예약일
  LocalDate visitDateTo;

  String type; // 상담유형(P,V ... 카카오)

  String status; // 상담상태 (10: 대기중, 컨택금지..)

  public ListConsultationsRequest(Integer page, Integer limit, Integer pageSize, String order, String direction, String search, String keyword, LocalDate createDateFrom, LocalDate createDateTo, LocalDate visitDateFrom, LocalDate visitDateTo, String type, String status) {
    super(page, limit, pageSize, order, direction, search, keyword);
    this.createDateFrom = createDateFrom;
    this.createDateTo = createDateTo;
    this.visitDateFrom = visitDateFrom;
    this.visitDateTo = visitDateTo;
    this.type = type;
    this.status = status;
  }
}
