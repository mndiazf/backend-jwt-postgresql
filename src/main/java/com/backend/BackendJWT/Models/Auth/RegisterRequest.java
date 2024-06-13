package com.backend.BackendJWT.Models.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String username;
    String lastname;
    String firstname;
    String email;
    String imgUrl;
    String password;
    String phoneNumber;
    String phoneNumber2;
    MultipartFile file;  // Agregar el campo para el archivo de imagen
}
