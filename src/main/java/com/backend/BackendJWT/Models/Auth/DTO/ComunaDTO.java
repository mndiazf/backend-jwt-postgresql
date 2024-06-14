package com.backend.BackendJWT.Models.Auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComunaDTO {
    private Long id;
    private String name;
    private Long regionId;
    private String regionName;

    // Getters and Setters
}
