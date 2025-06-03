package com.traumaticevolutions.tevosales_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.traumaticevolutions.tevosales_backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

/**
 * Configuración de seguridad para la API.
 * - Permite acceso libre al registro de usuario.
 * - Protege el resto de endpoints.
 * - Codificación de contraseñas usando BCrypt.
 * - Habilitamos anotaciones {@code @PreAuthorize} y con
 * {@code @EnableMethodSecurity(prePostEnabled = true)}.
 * 
 * @author Ángel Aragón
 */

@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    /**
     * Bean que define el codificador de contraseñas.
     * 
     * Utiliza el algoritmo BCrypt para encriptar contraseñas de forma segura.
     * 
     * @return PasswordEncoder configurado con BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean que define la cadena de filtros de seguridad.
     * 
     * - Desactiva CSRF para pruebas.
     * - Permite el acceso público al endpoint de registro de usuario.
     * - Protege el resto de los endpoints requiriendo autenticación JWT.
     * 
     * @param http Objeto HttpSecurity para configurar la seguridad.
     * @return SecurityFilterChain configurada.
     * @throws Exception Excepción en caso de error de configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/products/**", "/api/products").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
