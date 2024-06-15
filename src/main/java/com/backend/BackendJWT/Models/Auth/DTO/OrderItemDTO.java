package com.backend.BackendJWT.Models.Auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemDTO {

    private Long productoId;
    private String nombre;
    private String imgUrl;
    private Integer quantity;
    private Double precio;
}
