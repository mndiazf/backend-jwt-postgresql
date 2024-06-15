package com.backend.BackendJWT.Models.Auth;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "status_detail", nullable = false, length = 255)
    private String statusDetail;

    @Column(name = "date_approved")
    private LocalDateTime dateApproved;

    @Column(name = "payment_method_id", length = 50)
    private String paymentMethodId;

    @Column(name = "payment_method_type", length = 50)
    private String paymentMethodType;

    @Column(name = "transaction_amount", precision = 18, scale = 2)
    private BigDecimal transactionAmount;

    @Column(name = "payer_email", length = 255)
    private String payerEmail;

    @Column(name = "payer_id", length = 50)
    private String payerId;

    @Column(name = "order_id", length = 50)
    private String orderId;

    @Column(name = "operation_id", length = 50)
    private String operationId;

    @Column(name = "order_type", length = 50)
    private String orderType;
}
