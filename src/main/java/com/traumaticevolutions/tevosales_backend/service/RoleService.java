package com.traumaticevolutions.tevosales_backend.service;

import com.traumaticevolutions.tevosales_backend.model.Role;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Servicio para la gestión de roles.
 * Define las operaciones disponibles para la entidad Role.
 * 
 * @author Ángel Aragón
 */
public interface RoleService {

    /**
     * Obtiene todos los roles.
     * 
     * @return Lista de roles.
     */
    List<Role> findAll();

    /**
     * Busca un rol por su nombre.
     * 
     * @param name Nombre del rol.
     * @return Optional con el rol encontrado (si existe).
     */
    Optional<Role> findByName(String name);

    /**
     * Guarda un nuevo rol.
     * 
     * @param role Rol a guardar.
     * @return Rol guardado.
     */
    Role saveRole(Role role);

    /**
     * Busca roles con filtros aplicados a nombre de rol.
     * 
     * @param name     Nombre del rol.
     * @param pageable Información de paginación.
     * @return Página de roles filtrados.
     */
    Page<Role> findAllPagedAndFiltered(String name, Pageable pageable);

    /**
     * Actualiza un rol existente.
     * 
     * @param id      ID del rol.
     * @param roleDTO Datos a actualizar.
     * @return Rol actualizado.
     */
    Role updateRole(Long id, com.traumaticevolutions.tevosales_backend.dto.RoleResponseDTO roleDTO);

    /**
     * Elimina un rol por su ID.
     * 
     * @param id ID del rol.
     */
    void deleteRole(Long id);

}
