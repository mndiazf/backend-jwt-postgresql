package com.backend.BackendJWT.Services;

import com.backend.BackendJWT.Models.Auth.*;
import com.backend.BackendJWT.Jwt.JwtService;
import com.backend.BackendJWT.Repositories.RoleRepository;
import com.backend.BackendJWT.Repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired
    private RoleRepository roleRepository;

    public AuthResponse login(LoginRequest request) {
        try {
            // Intenta autenticar al usuario
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            // Busca el usuario en el repositorio
            UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            // Genera el token JWT para el usuario
            String token = jwtService.getToken(user);

            // Retorna la respuesta con el token
            return AuthResponse.builder()
                    .token(token)
                    .build();
        } catch (AuthenticationException e) {
            // Manejo de errores de autenticación
            throw new RuntimeException("Error de autenticación: " + e.getMessage());
        } catch (Exception e) {
            // Manejo de otros errores inesperados
            throw new RuntimeException("Error interno del servidor: " + e.getMessage());
        }
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("Username '" + request.getUsername() + "' is already registered");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email '" + request.getEmail() + "' is already associated with an account");
        }

        // Fetch the default role
        Role defaultRole = roleRepository.findByRoleName(ERole.USER)
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .phoneNumber2(request.getPhoneNumber2())  // Assuming phoneNumber2 can be directly passed.
                .role(defaultRole)  // Set the fetched role
                .build();

        userRepository.save(user);  // Persist the new user with the role in the database.

        // Generate token and return response
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
