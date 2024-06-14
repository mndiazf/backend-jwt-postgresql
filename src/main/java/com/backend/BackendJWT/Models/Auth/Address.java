package com.backend.BackendJWT.Models.Auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(nullable = false, length = 255)
    private String street;

    @NotNull
    @Size(min = 1, max = 10)
    @Column(nullable = false, length = 10)
    private String number;

    @Size(min = 1, max = 10)
    @Column(length = 10)
    private String apartmentNumber;  // Opcional

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comuna_id", nullable = false)
    @NotNull
    private Comuna comuna;

    @OneToOne(mappedBy = "address")
    private User user;
}
