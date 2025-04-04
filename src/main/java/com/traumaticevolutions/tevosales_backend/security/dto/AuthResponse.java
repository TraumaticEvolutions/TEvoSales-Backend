package com.traumaticevolutions.tevosales_backend.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO para la respuesta tras el login.
 * Contiene el token JWT generado si la autenticación es exitosa.
 * 
 * @author Ángel Aragón
 */
@Getter
@AllArgsConstructor
public class AuthResponse {
    private String token;
}
