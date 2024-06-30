package com.backend.BackendJWT.Controllers.Shop;

import com.backend.BackendJWT.Models.Shop.Marca;
import com.backend.BackendJWT.Services.Shop.MarcaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @PostMapping("/save")
    public ResponseEntity<Marca> saveMarca(@RequestBody Marca marca) {
        Marca savedMarca = marcaService.saveMarca(marca);

        if (savedMarca == null) {
            // Marca with the same name already exists. Return a 409 Conflict response.
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            return ResponseEntity.ok(savedMarca);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marca> updateMarca(@PathVariable Long id, @RequestBody Marca marca) {
        try {
            Marca updatedMarca = marcaService.updateMarca(id, marca);
            return ResponseEntity.ok(updatedMarca);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca no encontrada", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar la marca", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarca(@PathVariable Long id) {
        try {
            marcaService.deleteMarca(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca no encontrada", e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la marca", e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Marca>> getAllMarcas() {
        try {
            List<Marca> marcas = marcaService.getAllMarcas();
            return ResponseEntity.ok(marcas);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener las marcas", e);
        }
    }
}

