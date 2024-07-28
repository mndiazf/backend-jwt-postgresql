package com.backend.BackendJWT.Services;

import com.backend.BackendJWT.Controllers.GlobalExceptionHandler;
import com.backend.BackendJWT.Models.Auth.*;
import com.backend.BackendJWT.Jwt.JwtService;
import com.backend.BackendJWT.Repositories.RoleRepository;
import com.backend.BackendJWT.Repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (Exception e) {
            throw new GlobalExceptionHandler.AuthenticationFailedException("Invalid username or password");
        }

        UserDetails user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new GlobalExceptionHandler.UserNotFoundException("User not found"));
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        Role userRole = roleRepository.findByRoleName(ERole.USER)
                .orElseThrow(() -> new GlobalExceptionHandler.RoleNotFoundException("Role not found"));

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .role(userRole)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}