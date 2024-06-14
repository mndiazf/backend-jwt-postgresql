package com.backend.BackendJWT.Services;

import com.backend.BackendJWT.Models.Auth.Comuna;
import com.backend.BackendJWT.Models.Auth.Region;
import com.backend.BackendJWT.Repositories.Auth.ComunaRepository;
import com.backend.BackendJWT.Repositories.Auth.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    public List<Region> obtenerTodasLasRegiones() {
        return regionRepository.findAll();
    }

    public List<Comuna> obtenerComunasPorRegionId(Long regionId) {
        Optional<Region> region = regionRepository.findById(regionId);
        if (!region.isPresent()) {
            throw new IllegalArgumentException("No se encontró la región con el ID: " + regionId);
        }
        return comunaRepository.findByRegion(region.get());
    }
}
