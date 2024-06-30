package com.backend.BackendJWT.Services;

import com.backend.BackendJWT.Repositories.Auth.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;


    public List<OrderItemRepository.ProductDetailProjection> getProductDetailsByOrderId(Long orderId) {
        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException("Order ID must be a positive number");
        }

        List<OrderItemRepository.ProductDetailProjection> productDetails = orderItemRepository.findProductDetailsByOrderId(orderId);

        if (productDetails == null) {
            throw new RuntimeException("Failed to retrieve product details");
        }

        return productDetails;
    }
}

