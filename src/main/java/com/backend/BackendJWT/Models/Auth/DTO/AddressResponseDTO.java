package com.backend.BackendJWT.Models.Auth.DTO;

import lombok.Data;

@Data
public class AddressResponseDTO {
    private Long id;
    private String street;
    private String number;
    private String apartmentNumber;
    private ComunaDTO comuna;
    private UserDTO user;

    @Data
    public static class ComunaDTO {
        private Long id;
        private String name;
        private RegionDTO region;
    }

    @Data
    public static class RegionDTO {
        private Long id;
        private String name;
    }

    @Data
    public static class UserDTO {
        private Long id;
        private String username;
        private String firstname;
        private String lastname;
        private String email;
    }
}