package com.backend.BackendJWT.Models.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    private String nombre;
    private String descripcion;
    private String precio;
    private String marca;
    private MultipartFile imagen; // Cambiado a MultipartFile
    private int stock;
    private boolean oferta;
    private Set<String> category;
}
