package com.backend.BackendJWT.Models.Auth.DTO;


import lombok.Data;

@Data
public class GetUserDTO {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String imgUrl;
    private String phoneNumber;
    private String phoneNumber2;
}
