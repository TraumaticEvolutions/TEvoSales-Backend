package com.traumaticevolutions.tevosales_backend.service;

import java.util.List;
import java.util.Optional;

import com.traumaticevolutions.tevosales_backend.model.User;

/**
 * Servicio para la gestión de usuarios.
 * Define las operaciones disponibles para la entidad User.
 * @author Ángel Aragón
 */
public interface UserService {

    /**
     * Guardar un nuevo usuario.
     * @param user Usuario a guardar.
     * @return Usuario guardado.
     */
    User saveUser(User user);

    /**
     * Buscar un usuario por username.
     * @param username Username del usuario.
     * @return Optional con el usuario encontrado (si existe).
     */
    Optional<User> findByUsername(String username);

    /**
     * Buscar un usuario por su email.
     * @param email Email del usuario
     * @return Optional con el usuario encontrado (si existe).
     */
    Optional<User> findByEmail(String email);

    /**
     * Buscar un usuario por su nif.
     * @param nif NIF del usuario.
     * @return Optional con el usuario encontrado (si existe).
     */
    Optional<User> findByNIF(String nif);

    /**
     * Obtiene todos los usuarios.
     * @return Lista de usuarios.
     */
    List<User> getAllUsers();

}
