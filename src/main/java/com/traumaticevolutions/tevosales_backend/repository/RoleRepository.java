package com.traumaticevolutions.tevosales_backend.repository;

import com.traumaticevolutions.tevosales_backend.model.Role;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    /**
     * Busca todos los roles con paginación.
     * 
     * @param spec     Especificación para filtrar los roles.
     * @param pageable Información de paginación.
     * @return Página de roles.
     */
    Page<Role> findAll(Specification<Role> spec, Pageable pageable);

    /**
     * Busca roles por nombre con paginación.
     * 
     * @param name     Nombre del rol.
     * @param pageable Información de paginación.
     * @return Página de roles filtrados.
     */
    Page<Role> findByNameContainingIgnoreCase(String name, Pageable pageable);

}
