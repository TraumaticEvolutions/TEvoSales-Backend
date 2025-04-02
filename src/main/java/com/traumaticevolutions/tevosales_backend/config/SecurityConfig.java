package com.traumaticevolutions.tevosales_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad para la API.
 * Permite acceso libre al registro de usuario.
 * Protege el resto de endpoints.
 * @author Ángel
 */

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users/register").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic();

        return http.build();
    }
}
