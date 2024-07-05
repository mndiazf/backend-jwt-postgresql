package com.backend.BackendJWT.Controllers.Shop;

import com.backend.BackendJWT.Models.Shop.Categoria;
import com.backend.BackendJWT.Services.Shop.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
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
        try {
            Categoria updatedCategoria = categoriaService.updateCategoria(id, categoria);
            return ResponseEntity.ok(updatedCategoria);
        } catch (CategoriaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar la categoría", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable Long id) {
        try {
            categoriaService.deleteCategoria(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (CategoriaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la categoría", e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Categoria>> getAllCategorias() {
        try {
            List<Categoria> categorias = categoriaService.getAllCategorias();
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener las categorías", e);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class CategoriaNotFoundException extends RuntimeException {
        public CategoriaNotFoundException(String message) {
            super(message);
        }
    }


}

