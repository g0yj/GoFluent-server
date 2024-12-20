package com.lms.api.migration.mapping;

import java.util.HashMap;
import java.util.Map;

public class PaymentFieldMapping implements FieldMappingProvider {

  private final Map<String, FieldMapping> fieldMappings;

  public PaymentFieldMapping() {
    fieldMappings = new HashMap<>();
    fieldMappings.put("id", new FieldMapping(0, "고유값", null));
    fieldMappings.put("userId", new FieldMapping(2, "회원아이디", null));
    fieldMappings.put("orderId", new FieldMapping(3, "주문아이디", null));
    fieldMappings.put("paymentDate", new FieldMapping(4, "결제일", null));
    fieldMappings.put("type", new FieldMapping(5, "타입", null));
    fieldMappings.put("paymentMethod", new FieldMapping(6, "결제타입", null));
    fieldMappings.put("paymentAmount", new FieldMapping(7, "금액", 0));
    fieldMappings.put("accountHolder", new FieldMapping(8, "예금자", null));
    fieldMappings.put("accountNumber", new FieldMapping(9, "계좌", null));
    fieldMappings.put("cardCompany", new FieldMapping(10, "카드회사", null));
    fieldMappings.put("cardNumber", new FieldMapping(11, "카드번호", null));
    fieldMappings.put("installmentMonths", new FieldMapping(12, "할부개월", 0));
    fieldMappings.put("approvalNumber", new FieldMapping(13, "승인번호", null));
    fieldMappings.put("cancelAmount", new FieldMapping(14, "취소액", 0));
    fieldMappings.put("cancelDate", new FieldMapping(15, "취소일", null));
    fieldMappings.put("cancelManager", new FieldMapping(16, "취소처리자", null));
    fieldMappings.put("createdBy", new FieldMapping(17, "등록자아이디", null));
    fieldMappings.put("modifiedBy", new FieldMapping(17, "등록자아이디", null));
    fieldMappings.put("createdOn", new FieldMapping(18, "등록일", null));
    fieldMappings.put("modifiedOn", new FieldMapping(18, "등록일", null));
    fieldMappings.put("memo", new FieldMapping(19, "메모", null));
    fieldMappings.put("isReceiptIssued", new FieldMapping(20, "영수증여부", null));
    fieldMappings.put("receiptNumber", new FieldMapping(21, "영수증번호", null));
    fieldMappings.put("depositAmount", new FieldMapping(22, "예금액", 0));
    fieldMappings.put("transactionName", new FieldMapping(23, "거래명", null));
    fieldMappings.put("companyNumber", new FieldMapping(24, "회사번호", null));
    fieldMappings.put("accountTransactionDate", new FieldMapping(25, "계좌거래일", null));
  }

  @Override
  public FieldMapping getFieldMapping(String fieldName) {
    return fieldMappings.get(fieldName);
  }
}
