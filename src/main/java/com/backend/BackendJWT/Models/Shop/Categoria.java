package com.backend.BackendJWT.Models.Shop;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Data // Lombok annotation para getters, setters, toString, etc.
@NoArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 30)
    @Column(nullable = false, length = 30)
    private String nombre;

    public Categoria(String categoriaNombre) {
    }
}