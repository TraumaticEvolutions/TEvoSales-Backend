package com.traumaticevolutions.tevosales_backend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para representar un rol de usuario.
 * Contiene el ID y el nombre del rol.
 * 
 * @author Ángel Aragón
 */
@Getter
@Setter
public class RoleResponseDTO {
    /**
     * ID único del rol.
     */
    private Long id;
    /**
     * Nombre del rol.
     */
    private String name;
}