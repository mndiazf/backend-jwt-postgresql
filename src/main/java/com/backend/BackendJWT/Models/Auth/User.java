package com.backend.BackendJWT.Models.Auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    String username;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    String lastname;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    String firstname;

    @NotNull
    @Email
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    String email;

    @NotNull
    @Size(min = 8, max = 12)
    @Column(nullable = false, length = 12)
    String password;

    @NotNull
    @Size(min = 12, max = 12)
    @Column(nullable = false, length = 12)
    String phoneNumber;
    @Size(min = 12, max = 12)
    String phoneNumber2;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "role_id")
    @NotNull
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
