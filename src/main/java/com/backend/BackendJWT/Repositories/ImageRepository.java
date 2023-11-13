package com.backend.BackendJWT.Repositories;

import com.backend.BackendJWT.Models.Images.ImageEntity;
import com.backend.BackendJWT.Models.Product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity,Integer> {
}
