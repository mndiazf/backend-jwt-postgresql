package com.backend.BackendJWT.Models.Auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Long id;
    private String street;
    private String number;
    private String apartmentNumber;
    private Long comunaId;
    private String comunaName;
    private Long userId;
    private String userName;

    // Getters and Setters
}