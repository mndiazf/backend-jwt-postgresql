package com.backend.BackendJWT.Models.Auth.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionDTO {
    private Long id;
    private String name;

    // Getters and Setters
}
