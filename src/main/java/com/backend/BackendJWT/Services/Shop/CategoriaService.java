package com.backend.BackendJWT.Services.Shop;

import com.backend.BackendJWT.Models.Shop.Categoria;
import com.backend.BackendJWT.Repositories.Shop.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria saveCategoria(Categoria categoria) {
        if (categoriaRepository.existsByNombre(categoria.getNombre())) {
            // Marca with the same name already exists. Do nothing.
            return null;
        } else {
            Categoria categoria2 = convertirStringToCategoria(categoria.getNombre());
            return categoriaRepository.save(categoria2);
        }
    }

    public Categoria updateCategoria(Long id, Categoria categoria) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
        if (categoriaOptional.isPresent()) {
            Categoria categoria2 = convertirStringToCategoria(categoria.getNombre());
            Categoria existingCategoria = categoriaOptional.get();
            existingCategoria.setNombre(categoria2.getNombre());
            // Update other attributes if needed
            return categoriaRepository.save(existingCategoria);
        } else {
            throw new IllegalArgumentException("Categoria not found with id: " + id);
        }
    }


    public void deleteCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    public List<Categoria> getAllCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria convertirStringToCategoria(String nombre) {
        Categoria categoria = new Categoria();
        categoria.setNombre(capitalizar(nombre));
        return categoria;
    }

    public String capitalizar(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
    }

}
