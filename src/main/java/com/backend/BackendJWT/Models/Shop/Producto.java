package com.backend.BackendJWT.Models.Shop;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @NotNull
    @Size(min = 1, max = 120)
    @Column(nullable = false, length = 120)
    private String nombre;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    private Double precio;

    @NotNull
    @Column(nullable = false)
    private Integer stock;

    @NotNull
    @Size(min = 1, max = 1000)
    @Column(nullable = false, length = 1000)
    private String descripcion;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String imgUrl;


    @NotNull
    @Column(nullable = false)
    private Boolean descuento;

    @Column(name = "porcentaje_descuento")
    private Double porcentajeDescuento;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Categoria categoria;

    @ManyToOne
    @NotNull
    @JoinColumn(nullable = false)
    private Marca marca;
}