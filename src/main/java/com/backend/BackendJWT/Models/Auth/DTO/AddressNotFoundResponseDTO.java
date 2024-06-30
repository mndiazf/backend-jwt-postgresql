package com.backend.BackendJWT.Models.Auth.DTO;

import lombok.Data;

@Data
public class AddressNotFoundResponseDTO {
    private boolean addressFound;
    private String message;

    public AddressNotFoundResponseDTO(boolean addressFound, String message) {
        this.addressFound = addressFound;
        this.message = message;
    }

    // Getters y setters...
}