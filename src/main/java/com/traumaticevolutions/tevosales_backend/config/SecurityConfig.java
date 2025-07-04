package com.traumaticevolutions.tevosales_backend.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.traumaticevolutions.tevosales_backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

/**
 * Configuración de seguridad para la API.
 * - Permite acceso libre al registro de usuario.
 * - Protege el resto de endpoints.
 * - Codificación de contraseñas usando BCrypt.
 * - Habilitamos anotaciones {@code @PreAuthorize} y con
 * - CORS configurado para permitir solicitudes desde el frontend en
 * {@code @EnableMethodSecurity(prePostEnabled = true)}.
 * 
 * @author Ángel Aragón
 */

@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;
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
     * Configuración de seguridad HTTP.
     * 
     * Define las reglas de acceso a los endpoints de la API:
     * - Permite acceso público a registro y login.
     * - Permite acceso público a productos solo para peticiones GET.
     * - Restringe modificaciones y eliminaciones de pedidos y order-items a
     * usuarios con rol ADMIN.
     * - Requiere autenticación para el resto de peticiones.
     * 
     * @param http objeto HttpSecurity para configurar la seguridad HTTP.
     * @return SecurityFilterChain configurado.
     * @throws Exception si ocurre un error al configurar la seguridad.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/auth/**").permitAll()
                        .requestMatchers("/api/roles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/orders/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/orders/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/order-items/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/order-items/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configuración CORS para permitir solicitudes desde el frontend.
     * 
     * Permite solicitudes desde http://localhost:5173 con los métodos GET, POST,
     * PUT, DELETE.
     * 
     * @return CorsConfigurationSource configurado.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(allowedOrigins.split(",")));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
