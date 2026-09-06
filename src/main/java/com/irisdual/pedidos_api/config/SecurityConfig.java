package com.irisdual.pedidos_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitamos CSRF para pruebas locales con Postman
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permitimos el acceso libre a todos los endpoints
            );
        return http.build();
    }
}