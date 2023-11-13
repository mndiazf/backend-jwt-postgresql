package com.backend.BackendJWT.Models.Product;

import com.backend.BackendJWT.Models.Images.ImageEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "\"Product\"")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @Lob
    @NotBlank
    private String descripcion;

    @NotBlank
    private String precio;

    @NotBlank
    private String marca;

    @Column(columnDefinition = "TEXT")
    @Lob
    private String imagenBase64;

    private int stock;

    private boolean oferta;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = CategoryEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "Product_Category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<CategoryEntity> category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageEntity> images = new ArrayList<>();
}
