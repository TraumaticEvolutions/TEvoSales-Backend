package com.traumaticevolutions.tevosales_backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.traumaticevolutions.tevosales_backend.model.Role;
import com.traumaticevolutions.tevosales_backend.repository.RoleRepository;
import com.traumaticevolutions.tevosales_backend.service.RoleService;

import lombok.RequiredArgsConstructor;

/**
 * Implementación de la interfaz {@link RoleService}.
 * 
 * Contiene la lógica de negocio para la gestión de roles en la aplicación.
 * Permite buscar y guardar roles en la base de datos.
 * 
 * Este servicio es utilizado para asignar roles a los usuarios durante el
 * registro
 * y para gestionar la seguridad de la aplicación.
 * 
 * Utiliza Lombok con {@link RequiredArgsConstructor} para la inyección de
 * dependencias.
 * 
 * @author Ángel Aragón
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private static final List<String> PROTECTED_ROLES = List.of("ROLE_CLIENTE", "ROLE_ENTIDAD", "ROLE_ADMIN");

    /**
     * Repositorio para acceder a los datos de los roles.
     */
    private final RoleRepository roleRepository;

    /**
     * Obtiene todos los roles disponibles en la base de datos.
     * 
     * @return Lista de roles.
     */
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    /**
     * Busca un rol por su nombre.
     * 
     * @param name Nombre del rol.
     * @return Optional con el rol encontrado (si existe).
     */
    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    /**
     * Guarda un nuevo rol en la base de datos.
     * 
     * @param role Rol a guardar.
     * @return Rol guardado.
     */
    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    /**
     * Busca roles con filtros aplicados a nombre de rol.
     * 
     * @param name     Nombre del rol.
     * @param pageable Información de paginación.
     * @return Página de roles filtrados.
     */
    public Page<Role> findAllPagedAndFiltered(String name, Pageable pageable) {
        if (name != null && !name.isBlank()) {
            return roleRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            return roleRepository.findAll(pageable);
        }
    }

    /**
     * Actualiza un rol existente.
     * 
     * @param id      ID del rol.
     * @param roleDTO Datos a actualizar.
     * @return Rol actualizado.
     */
    @Override
    public Role updateRole(Long id, com.traumaticevolutions.tevosales_backend.dto.RoleResponseDTO roleDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
        if (PROTECTED_ROLES.contains(role.getName())) {
            throw new IllegalArgumentException("No se puede actualizar este rol protegido.");
        }
        role.setName(roleDTO.getName());
        return roleRepository.save(role);
    }

    /**
     * Elimina un rol por su ID.
     * 
     * @param id ID del rol.
     * @throws IllegalArgumentException si el rol es protegido y no se puede
     *                                  eliminar.
     */
    @Override
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
        if (PROTECTED_ROLES.contains(role.getName())) {
            throw new IllegalArgumentException("No se puede borrar este rol protegido.");
        }
        roleRepository.deleteById(id);
    }
}
