package com.traumaticevolutions.tevosales_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

/**
 * Configuración de seguridad para la API.
 * - Permite acceso libre al registro de usuario.
 * - Protege el resto de endpoints.
 * - Codificación de contraseñas usando BCrypt
 * @author Ángel Aragón
 */

@Configuration
public class SecurityConfig {

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
     * - Protege el resto de los endpoints requiriendo autenticación básica.
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
                .requestMatchers("/api/users/register").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
