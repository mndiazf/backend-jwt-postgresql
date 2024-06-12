package com.backend.BackendJWT.Models.Shop.Payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBuyer {
    private String title;
    private int quantity;
    private int unit_price;
}
