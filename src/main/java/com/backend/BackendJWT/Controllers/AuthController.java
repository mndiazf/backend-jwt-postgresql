package com.backend.BackendJWT.Controllers;

import com.backend.BackendJWT.Models.Auth.*;
import com.backend.BackendJWT.Models.Auth.DTO.*;
import com.backend.BackendJWT.Services.AddressService;
import com.backend.BackendJWT.Services.AuthService;
import com.backend.BackendJWT.Services.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private RegionService regionService;




    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestPart(value = "file", required = false) MultipartFile file,
                                      @RequestPart("username") String username,
                                      @RequestPart("password") String password,
                                      @RequestPart("firstname") String firstname,
                                      @RequestPart("lastname") String lastname,
                                      @RequestPart("email") String email,
                                      @RequestPart("phoneNumber") String phoneNumber,
                                      @RequestPart("phoneNumber2") String phoneNumber2) {
        try {
            RegisterRequest request = new RegisterRequest();
            request.setUsername(username);
            request.setPassword(password);
            request.setFirstname(firstname);
            request.setLastname(lastname);
            request.setEmail(email);
            request.setPhoneNumber(phoneNumber);
            request.setPhoneNumber2(phoneNumber2);
            request.setFile(file);

            AuthResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }


    @PutMapping("/update-user")
    public ResponseEntity<?> updateUserDetails(@RequestPart(value = "file", required = false) MultipartFile file,
                                               @RequestPart("username") String username,
                                               @RequestPart("firstname") String firstname,
                                               @RequestPart("lastname") String lastname,
                                               @RequestPart("email") String email,
                                               @RequestPart("phoneNumber") String phoneNumber,
                                               @RequestPart("phoneNumber2") String phoneNumber2) {
        try {
            RegisterRequest request = new RegisterRequest();
            request.setUsername(username);
            request.setFirstname(firstname);
            request.setLastname(lastname);
            request.setEmail(email);
            request.setPhoneNumber(phoneNumber);
            request.setPhoneNumber2(phoneNumber2);
            request.setFile(file);

            authService.updateUserDetails(request);
            return ResponseEntity.ok("User details updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }


    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody RecoverPassword request) {
        try {
            authService.updatePassword(request);
            return ResponseEntity.ok("Password updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }


    @PostMapping("/address/save")
    public ResponseEntity<Object> guardarDireccion(@RequestBody AddressDTO addressDTO) {
        try {
            AddressDTO addressGuardada = addressService.guardarDireccion(addressDTO);
            return new ResponseEntity<>(addressGuardada, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar una dirección existente
    @PutMapping("/address/{id}")
    public ResponseEntity<AddressDTO> editarDireccion(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        try {
            AddressDTO addressActualizada = addressService.editarDireccion(id, addressDTO);
            return new ResponseEntity<>(addressActualizada, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener una dirección por ID de usuario
    @GetMapping("/address/user/{userId}")
    public ResponseEntity<AddressDTO> obtenerDireccionPorUsuarioId(@PathVariable Long userId) {
        try {
            AddressDTO address = addressService.obtenerDireccionPorUsuarioId(userId);
            return new ResponseEntity<>(address, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener todas las regiones
    @GetMapping("/regions")
    public ResponseEntity<List<RegionDTO>> obtenerTodasLasRegiones() {
        try {
            List<Region> regiones = regionService.obtenerTodasLasRegiones();
            if (regiones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<RegionDTO> regionDTOs = regiones.stream().map(region -> {
                RegionDTO dto = new RegionDTO();
                dto.setId(region.getId());
                dto.setName(region.getName());
                return dto;
            }).collect(Collectors.toList());
            return new ResponseEntity<>(regionDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener todas las comunas según el ID de la región
    @GetMapping("/regions/{regionId}/comunas")
    public ResponseEntity<List<ComunaDTO>> obtenerComunasPorRegionId(@PathVariable Long regionId) {
        try {
            List<Comuna> comunas = regionService.obtenerComunasPorRegionId(regionId);
            if (comunas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            List<ComunaDTO> comunaDTOs = comunas.stream().map(comuna -> {
                ComunaDTO dto = new ComunaDTO();
                dto.setId(comuna.getId());
                dto.setName(comuna.getName());
                dto.setRegionId(comuna.getRegion().getId());
                dto.setRegionName(comuna.getRegion().getName());
                return dto;
            }).collect(Collectors.toList());
            return new ResponseEntity<>(comunaDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            UserDTO user = authService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }
}
