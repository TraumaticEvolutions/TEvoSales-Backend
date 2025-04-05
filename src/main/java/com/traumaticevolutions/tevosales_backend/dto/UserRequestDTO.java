package com.traumaticevolutions.tevosales_backend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO de entrada para el registro de usuario.
 * Recibe los datos necesarios para crear un nuevo usuario.
 * 
 * @author Ángel Aragón
 */
@Getter
@Setter
public class UserRequestDTO {
    private String username;
    private String names;
    private String email;
    private String password;
    private String nif;
}
