package com.backend.BackendJWT.Config;

import com.backend.BackendJWT.Config.Jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors
                        .configurationSource(request -> {
                            var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                            corsConfig.setAllowedOrigins(List.of("https://gentle-dune-082b8f81e.5.azurestaticapps.net")); // Cambia esto por tus dominios permitidos
                            corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                            corsConfig.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                            return corsConfig;
                        })
                )
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/shop/**").permitAll()
                                .requestMatchers("/api/categorias/**").permitAll()
                                .requestMatchers("/api/marcas/**").permitAll()
                                .requestMatchers("/email/send").permitAll()
                                .requestMatchers("/orders/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager ->
                        sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp.policyDirectives("script-src 'self'; object-src 'none'; base-uri 'self';"))
                        .frameOptions(frameOptions -> frameOptions.disable())  // No estático
                        .xssProtection(xss -> xss.disable())  // Deshabilitar protección XSS, si es necesario
                        .httpStrictTransportSecurity(hsts -> hsts
                                .maxAgeInSeconds(31536000)  // 1 año
                                .includeSubDomains(true)
                        )
                );
        return http.build();
    }
}