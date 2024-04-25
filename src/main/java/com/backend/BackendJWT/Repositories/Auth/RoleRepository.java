package com.backend.BackendJWT.Repositories.Auth;

import com.backend.BackendJWT.Models.Auth.ERole;
import com.backend.BackendJWT.Models.Auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ERole roleName);
}
