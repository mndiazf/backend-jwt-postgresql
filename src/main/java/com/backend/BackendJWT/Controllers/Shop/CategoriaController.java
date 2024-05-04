package com.backend.BackendJWT.Controllers.Shop;

import com.backend.BackendJWT.Models.Shop.Categoria;
import com.backend.BackendJWT.Services.Shop.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping("/save")
    public ResponseEntity<Categoria> saveCategoria(@RequestBody Categoria categoria) {
        Categoria savedCategoria = categoriaService.saveCategoria(categoria);

        if (savedCategoria == null) {
            // Categoria with the same name already exists. Return a 409 Conflict response.
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok(savedCategoria);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> updateCategoria(@PathVariable Long id, @RequestBody Categoria categoria) {
        Categoria updatedCategoria = categoriaService.updateCategoria(id, categoria);
        if (updatedCategoria == null) {
            // Categoria not found with the provided ID. Return a 404 Not Found response.
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedCategoria);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        categoriaService.deleteCategoria(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/all")
    public ResponseEntity<List<Categoria>> getAllCategorias() {
        List<Categoria> categorias = categoriaService.getAllCategorias();
        return ResponseEntity.ok(categorias);
    }
}

