package com.backend.BackendJWT.Models.Auth.DTO;


import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String imgUrl;
    private String phoneNumber;
    private String phoneNumber2;
    private String role;
}
