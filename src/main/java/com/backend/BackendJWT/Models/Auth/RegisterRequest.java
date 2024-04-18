package com.backend.BackendJWT.Models.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String username;
    String lastname;
    String firstname;
    String email;
    String password;
    String phoneNumber;
    String phoneNumber2;

}
