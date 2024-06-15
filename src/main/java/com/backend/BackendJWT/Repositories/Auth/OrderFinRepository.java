package com.backend.BackendJWT.Repositories.Auth;

import com.backend.BackendJWT.Models.Auth.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface OrderFinRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o.id AS orderId, os.statusName AS statusName, o.totalAmount AS totalAmount, p.operationId AS operationId " +
            "FROM Order o " +
            "JOIN o.orderStatus os " +
            "JOIN o.payment p " +
            "WHERE o.user.id = :userId AND os.id IN (4, 5)")
    List<OrderProjection> findOrdersByUserIdAndStatus(@Param("userId") Long userId);

    interface OrderProjection {
        Long getOrderId();
        String getStatusName();
        BigDecimal getTotalAmount();
        String getOperationId();
    }
}