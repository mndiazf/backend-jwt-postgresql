package com.backend.BackendJWT.Services;


import com.backend.BackendJWT.Repositories.Auth.OrderFinRepository;
import com.backend.BackendJWT.Repositories.Auth.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderFinRepository orderFinRepository;

    public List<OrderRepository.OrderProjection> getOrdersByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }

        List<OrderRepository.OrderProjection> orders = orderRepository.findOrdersByUserIdAndStatus(userId);

        if (orders == null) {
            throw new RuntimeException("Failed to retrieve orders");
        }

        return orders;
    }

    public List<OrderFinRepository.OrderProjection> getOrdersFinByUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }

        List<OrderFinRepository.OrderProjection> orders = orderFinRepository.findOrdersByUserIdAndStatus(userId);

        if (orders == null) {
            throw new RuntimeException("Failed to retrieve orders");
        }

        return orders;
    }
}

