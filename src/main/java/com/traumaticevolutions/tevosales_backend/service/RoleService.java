package com.traumaticevolutions.tevosales_backend.service;

import com.traumaticevolutions.tevosales_backend.model.Role;
import java.util.Optional;

/**
 * Servicio para la gestión de roles.
 * Define las operaciones disponibles para la entidad Role.
 * @author Ángel Aragón
 */
public interface RoleService {

    /**
     * Busca un rol por su nombre.
     * @param name Nombre del rol.
     * @return Optional con el rol encontrado (si existe).
     */
    Optional<Role> findByName(String name);

    /**
     * Guarda un nuevo rol.
     * @param role Rol a guardar.
     * @return Rol guardado.
     */
    Role saveRole(Role role);
}
