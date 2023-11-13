package com.backend.BackendJWT.Models.Auth;

import com.backend.BackendJWT.Models.Auth.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
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

    @NotBlank
    String username;
    @NotBlank
    String lastname;
    @NotBlank
    String firstname;
    @NotBlank
    String country;
    @NotBlank
    String password;
    @Enumerated(EnumType.STRING)
    Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of(new SimpleGrantedAuthority((role.name())));
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
