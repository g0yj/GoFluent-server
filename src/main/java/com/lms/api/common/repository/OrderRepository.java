package com.lms.api.common.repository;

import com.lms.api.common.dto.Order;
import com.lms.api.common.dto.Payment;
import com.lms.api.common.dto.Summary;
import com.lms.api.common.entity.OrderEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<OrderEntity, String>,
    QuerydslPredicateExecutor<OrderEntity> {

  List<OrderEntity> findAllByUserEntity_Id(String userId);

  @Query(nativeQuery = true, value = """
        SELECT u.name AS creatorName,
               SUM(p.payment_amount) AS paymentAmount,
               SUM(CASE WHEN p.payment_amount < 0 THEN -p.payment_amount ELSE 0 END) AS refundAmount
        FROM order_ o
        JOIN payment p ON o.id = p.order_id
        JOIN user_ u ON u.id = o.created_by
        WHERE p.payment_date BETWEEN :dateFrom AND :dateTo
        AND (:creatorName IS NULL OR u.name = :creatorName)
        GROUP BY u.name
        ORDER BY creatorName ASC
    """)
  List<Summary> listCalculateSummaryForPeriod(
      @Param("dateFrom") String dateFrom,
      @Param("dateTo") String dateTo,
      @Param("creatorName") String creatorName);

  @Query(nativeQuery = true, value = """
        SELECT DISTINCT o.id AS id,
               u1.name AS creatorName,
               CONCAT(pd.name, '/', op.months, '개월', '/', op.quantity, pd.quantity_unit) AS orderProductName,
               u.name AS userName,
               u.id AS userId,
               o.billing_amount AS billingAmount,
               o.payment_amount AS paymentAmount,
               r.refund_amount AS refundAmount,
               o.receivable_amount AS receivableAmount,
               o.created_on AS createdOn
        FROM order_ o
        JOIN user_ u ON u.id = o.user_id
        JOIN user_ u1 ON u1.id = o.created_by
        JOIN payment p ON o.id = p.order_id AND p.payment_date BETWEEN :dateFrom AND :dateTo
        JOIN order_product op ON o.id = op.order_id
        JOIN product pd ON op.product_id = pd.id
        LEFT OUTER JOIN refund r ON r.order_product_id = o.id
        WHERE p.order_id IS NOT NULL
        AND (:creatorName IS NULL OR u1.name = :creatorName)
        ORDER BY o.created_on
    """)
  List<Order> listCalculatesOrderForPeriod(
      @Param("dateFrom") String dateFrom,
      @Param("dateTo") String dateTo,
      @Param("creatorName") String creatorName);

  @Query(nativeQuery = true, value = """
        SELECT o.id AS orderId,
               p.id AS paymentId,
               u1.name AS creatorName,
               p.created_on AS createdOn,
               p.type AS paymentType,
               p.transaction_name AS transactionName,
               p.installment_months AS installmentMonths,
               p.card_number AS cardNumber,
               p.payment_amount AS paymentAmount,
               0 as refundAmount
        FROM order_ o
        JOIN user_ u ON u.id = o.user_id
        JOIN user_ u1 ON u1.id = o.created_by
        JOIN payment p ON o.id = p.order_id AND p.payment_date BETWEEN :dateFrom AND :dateTo
        WHERE (:creatorName IS NULL OR u1.name = :creatorName)
        ORDER BY o.created_on
    """)
  List<Payment> listCalculatesPaymentForPeriod(
      @Param("dateFrom") String dateFrom,
      @Param("dateTo") String dateTo,
      @Param("creatorName") String creatorName);
}
