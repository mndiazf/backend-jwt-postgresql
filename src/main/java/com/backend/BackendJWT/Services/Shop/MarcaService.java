package com.backend.BackendJWT.Services.Shop;

import com.backend.BackendJWT.Models.Shop.Marca;
import com.backend.BackendJWT.Repositories.Shop.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public Marca saveMarca(Marca marca) {
        Marca marca2 = convertirStringToMarca(marca.getNombre());
        if (marcaRepository.existsByNombre(marca2.getNombre())) {
            // Marca with the same name already exists. Do nothing.
            return null;
        } else {
            return marcaRepository.save(marca2);
        }
    }

    public Marca updateMarca(Long id, Marca marca) {
        Optional<Marca> marcaOptional = marcaRepository.findById(id);
        if (marcaOptional.isPresent()) {
            Marca marca2 = convertirStringToMarca(marca.getNombre());
            Marca existingMarca = marcaOptional.get();
            existingMarca.setNombre(marca2.getNombre());
            // Update other attributes if needed
            return marcaRepository.save(existingMarca);
        } else {
            throw new IllegalArgumentException("Marca not found with id: " + id);
        }
    }


    public void deleteMarca(Long id) {
        marcaRepository.deleteById(id);
    }

    public List<Marca> getAllMarcas() {
        return marcaRepository.findAll();
    }


    public Marca convertirStringToMarca(String nombre) {
        Marca marca = new Marca();
        marca.setNombre(capitalizar(nombre));
        return marca;
    }

    public String capitalizar(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        }
    }


}
