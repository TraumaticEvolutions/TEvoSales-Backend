package com.traumaticevolutions.tevosales_backend.dto;

import java.util.List;

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
    /**
     * Email del usuario.
     */
    private String email;
    /**
     * NIF del usuario.
     */
    private String nif;
    /**
     * Número de pedidos realizados por el usuario.
     */
    private Integer ordersCount;
    /**
     * Lista de roles asignados al usuario.
     * Se devuelve como una lista de nombres de rol.
     */
    private List<String> roles;

}
