package com.traumaticevolutions.tevosales_backend.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.traumaticevolutions.tevosales_backend.model.Role;
import com.traumaticevolutions.tevosales_backend.repository.RoleRepository;
import com.traumaticevolutions.tevosales_backend.service.RoleService;

import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio RoleService.
 * Contiene la lógica de negocio para la gestión de roles.
 * @author Ángel Aragón
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
    
    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

}
