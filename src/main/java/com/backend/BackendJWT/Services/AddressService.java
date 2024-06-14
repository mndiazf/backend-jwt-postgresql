package com.backend.BackendJWT.Services;


import com.backend.BackendJWT.Models.Auth.Address;
import com.backend.BackendJWT.Models.Auth.Comuna;
import com.backend.BackendJWT.Models.Auth.DTO.AddressDTO;
import com.backend.BackendJWT.Models.Auth.User;
import com.backend.BackendJWT.Repositories.Auth.AddressRepository;
import com.backend.BackendJWT.Repositories.Auth.ComunaRepository;
import com.backend.BackendJWT.Repositories.Auth.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public AddressDTO guardarDireccion(AddressDTO addressDTO) {
        Long comunaId = addressDTO.getComunaId();
        Long userId = addressDTO.getUserId();

        Comuna comuna = comunaRepository.findById(comunaId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la comuna con el ID: " + comunaId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el usuario con el ID: " + userId));

        Address address;

        if (user.getAddress() != null) {
            // Si el usuario ya tiene una dirección, actualízala
            address = user.getAddress();
            address.setStreet(addressDTO.getStreet());
            address.setNumber(addressDTO.getNumber());
            address.setApartmentNumber(addressDTO.getApartmentNumber());
            address.setComuna(comuna);
        } else {
            // Si el usuario no tiene una dirección, crea una nueva
            address = Address.builder()
                    .street(addressDTO.getStreet())
                    .number(addressDTO.getNumber())
                    .apartmentNumber(addressDTO.getApartmentNumber())
                    .comuna(comuna)
                    .user(user)
                    .build();
            address = addressRepository.save(address);
            user.setAddress(address);
            userRepository.save(user);
        }

        Address savedAddress = addressRepository.save(address);
        return convertToDto(savedAddress);
    }

    @Transactional
    public AddressDTO editarDireccion(Long id, AddressDTO addressDTO) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la dirección con el ID: " + id));

        address.setStreet(addressDTO.getStreet());
        address.setNumber(addressDTO.getNumber());
        address.setApartmentNumber(addressDTO.getApartmentNumber());

        Long comunaId = addressDTO.getComunaId();
        Comuna comuna = comunaRepository.findById(comunaId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la comuna con el ID: " + comunaId));
        address.setComuna(comuna);

        Long userId = addressDTO.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el usuario con el ID: " + userId));
        address.setUser(user);

        Address updatedAddress = addressRepository.save(address);

        // Actualiza el address_id del usuario con la dirección actualizada
        user.setAddress(updatedAddress);
        userRepository.save(user);

        return convertToDto(updatedAddress);
    }

    public AddressDTO obtenerDireccionPorUsuarioId(Long userId) {
        Address address = addressRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la dirección para el usuario con el ID: " + userId));
        return convertToDto(address);
    }

    private AddressDTO convertToDto(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .number(address.getNumber())
                .apartmentNumber(address.getApartmentNumber())
                .comunaId(address.getComuna().getId())
                .comunaName(address.getComuna().getName())
                .userId(address.getUser().getId())
                .userName(address.getUser().getUsername())
                .build();
    }
}
