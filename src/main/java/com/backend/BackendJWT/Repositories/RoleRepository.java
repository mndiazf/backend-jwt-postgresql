package com.backend.BackendJWT.Repositories;

import com.backend.BackendJWT.Models.Auth.ERole;
import com.backend.BackendJWT.Models.Auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByRoleName(ERole roleName);
}
