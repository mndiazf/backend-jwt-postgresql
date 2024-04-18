package com.backend.BackendJWT.Services;

import com.backend.BackendJWT.Models.Auth.AuthResponse;
import com.backend.BackendJWT.Models.Auth.LoginRequest;
import com.backend.BackendJWT.Models.Auth.RegisterRequest;
import com.backend.BackendJWT.Jwt.JwtService;
import com.backend.BackendJWT.Models.Auth.Role;
import com.backend.BackendJWT.Models.Auth.User;
import com.backend.BackendJWT.Repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();

    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("Username '" + request.getUsername() + "' is already registered");
        }

        // Check for existing email (optional, uncomment if needed)
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email '" + request.getEmail() + "' is already associated with an account");
        }
        User user = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode( request.getPassword()))
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .phoneNumber(request.getPhoneNumber())
            .phoneNumber2(Optional.ofNullable(request.getPhoneNumber2()).orElse(null))
            .role(Role.USER)

            .build();



        userRepository.save(user);

        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
        
    }
    // Custom exception classes (create separate files for these)
    public class UsernameAlreadyExistsException extends RuntimeException {
        public UsernameAlreadyExistsException(String message) {
            super(message);
        }
    }

    public class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException(String message) {
            super(message);
        }
    }
}
