package com.backend.BackendJWT.Config;

import com.backend.BackendJWT.Models.Auth.ERole;
import com.backend.BackendJWT.Models.Auth.Role;
import com.backend.BackendJWT.Repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        insertRoleIfNotExists(ERole.USER);
        insertRoleIfNotExists(ERole.ADMIN);
    }

    private void insertRoleIfNotExists(ERole roleName) {
        Optional<Role> existingRole = roleRepository.findByRoleName(roleName);
        if (existingRole.isEmpty()) {
            Role role = new Role();
            role.setRoleName(roleName);
            roleRepository.save(role);
        }
    }
}