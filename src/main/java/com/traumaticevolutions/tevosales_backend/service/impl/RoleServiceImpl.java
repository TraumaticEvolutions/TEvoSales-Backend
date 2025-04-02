package com.traumaticevolutions.tevosales_backend.service.impl;

import java.util.Optional;

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
 * Este servicio es utilizado para asignar roles a los usuarios durante el registro
 * y para gestionar la seguridad de la aplicación.
 * 
 * Utiliza Lombok con {@link RequiredArgsConstructor} para la inyección de dependencias.
 * 
 * @author Ángel Aragón
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    /**
     * Repositorio para acceder a los datos de los roles.
     */
    private final RoleRepository roleRepository;

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
}
