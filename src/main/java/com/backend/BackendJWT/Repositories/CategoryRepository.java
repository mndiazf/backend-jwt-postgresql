package com.backend.BackendJWT.Repositories;

import com.backend.BackendJWT.Models.Product.CategoryEntity;
import com.backend.BackendJWT.Models.Product.ECategory;
import com.backend.BackendJWT.Models.Product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Integer> {

    CategoryEntity findByName(ECategory name);
}
