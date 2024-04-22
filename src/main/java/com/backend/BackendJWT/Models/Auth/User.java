package com.backend.BackendJWT.Models.Auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "\"user\"")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    Integer id;
    String username;
    String lastname;
    String firstname;
    String email;
    String password;
    String phoneNumber;
    String phoneNumber2;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;  // Ensure there is no @Enumerated here as Role is an entity


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getRoleName().name()));
    }
    @Override
    public boolean isAccountNonExpired() {
       return true;
    }
    @Override
    public boolean isAccountNonLocked() {
       return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
