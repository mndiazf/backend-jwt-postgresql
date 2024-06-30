package com.backend.BackendJWT.Services;


import com.backend.BackendJWT.Models.Auth.Address;
import com.backend.BackendJWT.Models.Auth.Comuna;
import com.backend.BackendJWT.Models.Auth.DTO.AddressDTO;
import com.backend.BackendJWT.Models.Auth.DTO.AddressResponseDTO;
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
    public AddressResponseDTO saveAddress(AddressDTO addressDTO) {
        Optional<User> userOptional = userRepository.findById(addressDTO.getUserId());
        Optional<Comuna> comunaOptional = comunaRepository.findById(addressDTO.getComunaId());

        if (userOptional.isEmpty() || comunaOptional.isEmpty()) {
            throw new RuntimeException("User or Comuna not found");
        }

        User user = userOptional.get();
        Comuna comuna = comunaOptional.get();

        Address address = Address.builder()
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .apartmentNumber(addressDTO.getApartmentNumber())
                .comuna(comuna)
                .user(user)
                .build();

        address = addressRepository.save(address);
        user.setAddress(address);
        userRepository.save(user);

        return convertToDTO(address);
    }

    @Transactional
    public AddressResponseDTO updateAddress(Long userId, AddressDTO addressDTO) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Comuna> comunaOptional = comunaRepository.findById(addressDTO.getComunaId());

        if (userOptional.isEmpty() || comunaOptional.isEmpty()) {
            throw new RuntimeException("User or Comuna not found");
        }

        User user = userOptional.get();
        Address address = user.getAddress();

        if (address == null) {
            throw new RuntimeException("Address not found for user");
        }

        Comuna comuna = comunaOptional.get();
        address.setStreet(addressDTO.getStreet());
        address.setNumber(addressDTO.getNumber());
        address.setApartmentNumber(addressDTO.getApartmentNumber());
        address.setComuna(comuna);

        address = addressRepository.save(address);

        return convertToDTO(address);
    }

    public Optional<AddressResponseDTO> getAddressByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent() && userOptional.get().getAddress() != null) {
            Address address = userOptional.get().getAddress();
            return Optional.of(convertToDTO(address));
        }
        return Optional.empty();
    }

    private AddressResponseDTO convertToDTO(Address address) {
        AddressResponseDTO dto = new AddressResponseDTO();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setNumber(address.getNumber());
        dto.setApartmentNumber(address.getApartmentNumber());

        AddressResponseDTO.ComunaDTO comunaDTO = new AddressResponseDTO.ComunaDTO();
        comunaDTO.setId(address.getComuna().getId());
        comunaDTO.setName(address.getComuna().getName());

        AddressResponseDTO.RegionDTO regionDTO = new AddressResponseDTO.RegionDTO();
        regionDTO.setId(address.getComuna().getRegion().getId());
        regionDTO.setName(address.getComuna().getRegion().getName());

        comunaDTO.setRegion(regionDTO);
        dto.setComuna(comunaDTO);

        AddressResponseDTO.UserDTO userDTO = new AddressResponseDTO.UserDTO();
        userDTO.setId(address.getUser().getId());
        userDTO.setUsername(address.getUser().getUsername());
        userDTO.setFirstname(address.getUser().getFirstname());
        userDTO.setLastname(address.getUser().getLastname());
        userDTO.setEmail(address.getUser().getEmail());
        dto.setUser(userDTO);

        return dto;
    }
}

