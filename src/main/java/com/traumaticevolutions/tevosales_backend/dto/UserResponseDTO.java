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
    private Long id;
    private String username;
    private String name;
    private String email;
    private String nif;
    
}
