package com.backend.BackendJWT.Controllers;

import com.backend.BackendJWT.Models.Auth.AuthResponse;
import com.backend.BackendJWT.Models.Auth.RecoverPassword;
import com.backend.BackendJWT.Services.AuthService;
import com.backend.BackendJWT.Models.Auth.LoginRequest;
import com.backend.BackendJWT.Models.Auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestPart("file") MultipartFile file,
                                      @RequestPart("username") String username,
                                      @RequestPart("password") String password,
                                      @RequestPart("firstname") String firstname,
                                      @RequestPart("lastname") String lastname,
                                      @RequestPart("email") String email,
                                      @RequestPart("imgUrl") String imgUrl,
                                      @RequestPart("phoneNumber") String phoneNumber,
                                      @RequestPart("phoneNumber2") String phoneNumber2) {
        try {
            RegisterRequest request = new RegisterRequest();
            request.setUsername(username);
            request.setPassword(password);
            request.setFirstname(firstname);
            request.setLastname(lastname);
            request.setEmail(email);
            request.setImgUrl(imgUrl);
            request.setPhoneNumber(phoneNumber);
            request.setPhoneNumber2(phoneNumber2);
            request.setFile(file);

            AuthResponse response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @PutMapping("/update-user")
    public ResponseEntity<?> updateUserDetails(@RequestPart("file") MultipartFile file,
                                               @RequestPart("username") String username,
                                               @RequestPart("firstname") String firstname,
                                               @RequestPart("lastname") String lastname,
                                               @RequestPart("email") String email,
                                               @RequestPart("imgUrl") String imgUrl,
                                               @RequestPart("phoneNumber") String phoneNumber,
                                               @RequestPart("phoneNumber2") String phoneNumber2) {
        try {
            RegisterRequest request = new RegisterRequest();
            request.setUsername(username);
            request.setFirstname(firstname);
            request.setLastname(lastname);
            request.setEmail(email);
            request.setImgUrl(imgUrl);
            request.setPhoneNumber(phoneNumber);
            request.setPhoneNumber2(phoneNumber2);
            request.setFile(file);

            authService.updateUserDetails(request);
            return ResponseEntity.ok("User details updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody RecoverPassword request) {
        try {
            authService.updatePassword(request);
            return ResponseEntity.ok("Password updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }


}
