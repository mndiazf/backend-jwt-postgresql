package com.backend.BackendJWT.Controllers.Shop;

import com.backend.BackendJWT.Models.Shop.Marca;
import com.backend.BackendJWT.Services.Shop.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
        Marca updatedMarca = marcaService.updateMarca(id, marca);
        if (updatedMarca == null) {
            // Marca not found with the provided ID. Return a 404 Not Found response.
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedMarca);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarca(@PathVariable Long id) {
        marcaService.deleteMarca(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/all")
    public ResponseEntity<List<Marca>> getAllMarcas() {
        List<Marca> marcas = marcaService.getAllMarcas();
        return ResponseEntity.ok(marcas);
    }
}

