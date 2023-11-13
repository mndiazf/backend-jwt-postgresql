package com.backend.BackendJWT.Models.Images;

import com.backend.BackendJWT.Models.Product.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "\"Image\"")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String base64Data;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    // Getters y setters
}
