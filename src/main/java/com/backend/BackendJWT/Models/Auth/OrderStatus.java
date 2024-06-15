package com.backend.BackendJWT.Models.Auth;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status_name", nullable = false)
    private String statusName;
}
