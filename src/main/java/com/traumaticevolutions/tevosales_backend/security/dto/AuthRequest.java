package com.traumaticevolutions.tevosales_backend.security.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para la solicitud de login.
 * Contiene el username y la contraseña del usuario.
 * 
 * @author Ángel Aragón
 */
@Getter
@Setter
public class AuthRequest {
    private String username;
    private String password;
}
