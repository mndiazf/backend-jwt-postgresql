package com.backend.BackendJWT.Repositories.Shop;

import com.backend.BackendJWT.Models.Shop.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}
