package com.traumaticevolutions.tevosales_backend.repository;

import com.traumaticevolutions.tevosales_backend.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad User.
 * Permite gestionar operaciones CRUD y consultas específicas.
 * 
 * @author Ángel Aragón
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * Busca un usuario por su email.
     * 
     * @param email Email del usuario.
     * @return Optional con el usuario encontrado (si existe).
     */
    Optional<User> findByEmail(String email);

    /**
     * Busca un usuario por su username.
     * 
     * @param username Nombre de usuario.
     * @return Optional con el usuario encontrado (si existe).
     */
    Optional<User> findByUsername(String username);

    /**
     * Busca un usuario por su NIF.
     * 
     * @param nif NIF del usuario.
     * @return Optional con el usuario encontrado (si existe).
     */
    Optional<User> findByNif(String nif);
}
