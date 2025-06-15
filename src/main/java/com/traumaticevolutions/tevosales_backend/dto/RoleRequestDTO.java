package com.traumaticevolutions.tevosales_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para representar un rol de usuario.
 * Contiene el nombre del rol.
 * 
 * @author Ángel Aragón
 */
@Getter
@Setter
public class RoleRequestDTO {
    /**
     * Nombre del rol.
     * Debe tener entre 3 y 50 caracteres.
     * No puede estar vacío.
     */
    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre del rol debe tener entre 3 y 50 caracteres")
    private String name;
}