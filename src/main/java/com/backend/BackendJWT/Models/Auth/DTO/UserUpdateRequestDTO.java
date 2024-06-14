package com.backend.BackendJWT.Models.Auth.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserUpdateRequestDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String phoneNumber2;
    private MultipartFile file;

    // Getters y setters para todos los campos
}
