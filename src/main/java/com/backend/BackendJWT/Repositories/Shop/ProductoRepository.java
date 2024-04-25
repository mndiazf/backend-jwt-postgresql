package com.backend.BackendJWT.Repositories.Shop;

import com.backend.BackendJWT.Models.Shop.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}