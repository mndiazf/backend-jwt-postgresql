package com.backend.BackendJWT.Repositories.Shop;

import com.backend.BackendJWT.Models.Shop.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("SELECT p FROM Producto p ORDER BY CASE WHEN p.stock = 0 THEN 1 ELSE 0 END, FUNCTION('RAND')")
    List<Producto> findAllProductosOrdered();
}