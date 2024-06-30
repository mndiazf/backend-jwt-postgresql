package com.backend.BackendJWT.Controllers;


import com.backend.BackendJWT.Repositories.Auth.OrderFinRepository;
import com.backend.BackendJWT.Repositories.Auth.OrderItemRepository;
import com.backend.BackendJWT.Repositories.Auth.OrderRepository;
import com.backend.BackendJWT.Services.OrderItemService;
import com.backend.BackendJWT.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/orders")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;


    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable Long userId) {
        try {
            if (userId == null || userId <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID must be a positive number");
            }

            List<OrderRepository.OrderProjection> orders = orderService.getOrdersByUserId(userId);

            if (orders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found for the given user ID");
            }

            return ResponseEntity.ok(orders);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping("/fin/{userId}")
    public ResponseEntity<?> getFinOrdersByUserId(@PathVariable Long userId) {
        try {
            if (userId == null || userId <= 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID must be a positive number");
            }

            List<OrderFinRepository.OrderProjection> orders = orderService.getOrdersFinByUserId(userId);

            if (orders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found for the given user ID");
            }

            return ResponseEntity.ok(orders);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping("/products/{orderId}")
    public ResponseEntity<?> getProductDetailsByOrderId(@PathVariable Long orderId) {
        try {
            List<OrderItemRepository.ProductDetailProjection> productDetails = orderItemService.getProductDetailsByOrderId(orderId);

            if (productDetails.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found for the given order ID");
            }

            return ResponseEntity.ok(productDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }
}

