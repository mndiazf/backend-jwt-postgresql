package com.backend.BackendJWT.Repositories.Auth;

import com.backend.BackendJWT.Models.Auth.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT p.nombre AS productName, p.imgUrl AS imgUrl, oi.quantity AS quantity, oi.price AS unitPrice " +
            "FROM OrderItem oi " +
            "JOIN oi.producto p " +
            "WHERE oi.order.id = :orderId")
    List<ProductDetailProjection> findProductDetailsByOrderId(@Param("orderId") Long orderId);

    interface ProductDetailProjection {
        String getProductName();
        String getImgUrl();
        Integer getQuantity();
        BigDecimal getUnitPrice();
    }
}