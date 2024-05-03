package com.backend.BackendJWT.Repositories.Shop;

import com.backend.BackendJWT.Models.Shop.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
    Optional<Marca> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}