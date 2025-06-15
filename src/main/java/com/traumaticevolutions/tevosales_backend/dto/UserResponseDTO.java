package com.traumaticevolutions.tevosales_backend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO de salida para devolver datos del usuario al frontend.
 * No incluye información sensible como contraseñas o roles.
 * 
 * @author Ángel Aragón
 */
@Getter
@Setter
public class UserResponseDTO {
    /**
     * ID del usuario.
     */
    private Long id;
    /**
     * Nombre de usuario único.
     */
    private String username;
    /**
     * Nombre completo del usuario.
     */
    private String name;

}
