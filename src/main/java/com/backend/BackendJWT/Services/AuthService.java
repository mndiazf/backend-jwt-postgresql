package com.backend.BackendJWT.Services;

import com.backend.BackendJWT.Models.Auth.*;
import com.backend.BackendJWT.Config.Jwt.JwtService;
import com.backend.BackendJWT.Models.Auth.DTO.*;
import com.backend.BackendJWT.Repositories.Auth.RoleRepository;
import com.backend.BackendJWT.Repositories.Auth.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final BlobStorageService blobStorageService;

    private static final String DEFAULT_IMAGE_URL = "https://mousecat1.blob.core.windows.net/mousecat/cat-illustration-free-vector.jpg";

    @Autowired
    private RoleRepository roleRepository;

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            String token = jwtService.getToken(user);
            return AuthResponse.builder().token(token).build();
        } catch (AuthenticationException e) {
            throw new RuntimeException("Error de autenticación: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error interno del servidor: " + e.getMessage());
        }
    }

    public AuthResponse register(RegisterRequest request) {
        try {
            if (userRepository.existsByUsername(request.getUsername())) {
                throw new UsernameAlreadyExistsException("Username '" + request.getUsername() + "' is already registered");
            }
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new EmailAlreadyExistsException("Email '" + request.getEmail() + "' is already associated with an account");
            }

            Role defaultRole = roleRepository.findByRoleName(ERole.USER)
                    .orElseThrow(() -> new RuntimeException("Default role not found"));

            String imageUrl = DEFAULT_IMAGE_URL; // Set default image URL
            if (request.getFile() != null && !request.getFile().isEmpty()) {
                imageUrl = blobStorageService.uploadFile(request.getFile(), request.getFile().getOriginalFilename());
            }

            User user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .imgUrl(imageUrl)
                    .phoneNumber(request.getPhoneNumber())
                    .phoneNumber2(request.getPhoneNumber2())
                    .role(defaultRole)
                    .build();
            userRepository.save(user);

            return AuthResponse.builder().token(jwtService.getToken(user)).build();
        } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error interno del servidor: " + e.getMessage());
        }
    }


    public void updateUserDetails(UserUpdateRequestDTO request) {
        try {
            User user = userRepository.findById(request.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            if (request.getFile() != null && !request.getFile().isEmpty()) {
                // Eliminar la imagen anterior si existe
                if (user.getImgUrl() != null && !user.getImgUrl().isEmpty()) {
                    String fileName = user.getImgUrl().substring(user.getImgUrl().lastIndexOf('/') + 1);
                    blobStorageService.deleteFile(fileName);
                }

                // Subir la nueva imagen
                String newImageUrl = blobStorageService.uploadFile(request.getFile(), request.getFile().getOriginalFilename());
                user.setImgUrl(newImageUrl);
            }
            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setPhoneNumber2(request.getPhoneNumber2());
            userRepository.save(user);
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException("Usuario no encontrado: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error interno del servidor: " + e.getMessage());
        }
    }



    // Método para actualizar la contraseña del usuario
    public void updatePassword(RecoverPassword request) {
        try {
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Usuario con el email '" + request.getEmail() + "' no encontrado"));
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException("Usuario no encontrado: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error interno del servidor: " + e.getMessage());
        }
    }

    public GetUserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return mapToUserDTO(user);
    }

    private GetUserDTO mapToUserDTO(User user) {
        GetUserDTO getUserDTO = new GetUserDTO();
        getUserDTO.setUsername(user.getUsername());
        getUserDTO.setFirstname(user.getFirstname());
        getUserDTO.setLastname(user.getLastname());
        getUserDTO.setEmail(user.getEmail());
        getUserDTO.setImgUrl(user.getImgUrl());
        getUserDTO.setPhoneNumber(user.getPhoneNumber());
        getUserDTO.setPhoneNumber2(user.getPhoneNumber2());
        return getUserDTO;
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

