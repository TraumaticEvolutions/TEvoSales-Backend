package com.traumaticevolutions.tevosales_backend.repository;

import com.traumaticevolutions.tevosales_backend.model.Role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad Role.
 * 
 * @author Ángel Aragón
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Busca un rol por su nombre.
     * 
     * @param name Nombre del rol.
     * @return Optional con el rol encontrado (si existe).
     */
    Optional<Role> findByName(String name);

}
