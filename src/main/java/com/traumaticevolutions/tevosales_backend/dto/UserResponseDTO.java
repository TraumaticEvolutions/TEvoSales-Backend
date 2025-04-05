package com.traumaticevolutions.tevosales_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO de salida para devolver datos del usuario al frontend.
 * No incluye información sensible como contraseñas.
 * 
 * @author Ángel Aragón
 */
@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String username;
    private String names;
    private String email;
    private String nif;
    private List<String> roles;
    
}
