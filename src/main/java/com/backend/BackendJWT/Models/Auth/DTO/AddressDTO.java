package com.backend.BackendJWT.Models.Auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AddressDTO {
    @NotNull
    @Size(min = 1, max = 255)
    private String street;

    @NotNull
    @Size(min = 1, max = 10)
    private String number;

    @Size(min = 1, max = 10)
    private String apartmentNumber;

    @NotNull
    private Long comunaId;

    @NotNull
    private Long userId;
}