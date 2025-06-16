package com.traumaticevolutions.tevosales_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.traumaticevolutions.tevosales_backend.dto.UserOrdersStatsDTO;
import com.traumaticevolutions.tevosales_backend.model.User;

/**
 * Servicio para la gestión de usuarios.
 * Define las operaciones disponibles para la entidad User.
 * 
 * @author Ángel Aragón
 */
public interface UserService {

    /**
     * Guardar un nuevo usuario.
     * 
     * @param user Usuario a guardar.
     * @return Usuario guardado.
     */
    User saveUser(User user);

    /**
     * Buscar un usuario por su id.
     * 
     * @param id ID del usuario.
     * @return Optional con el usuario encontrado (si existe).
     */
    Optional<User> findById(Long id);

    /**
     * Buscar un usuario por email.
     * 
     * @param email Email del usuario.
     * @return Optional con el usuario encontrado (si existe).
     */
    Optional<User> findByEmail(String email);

    /**
     * Buscar un usuario por username.
     * 
     * @param username Username del usuario.
     * @return Optional con el usuario encontrado (si existe).
     */
    Optional<User> findByUsername(String username);

    /**
     * Buscar un usuario por su nif.
     * 
     * @param nif NIF del usuario.
     * @return Optional con el usuario encontrado (si existe).
     */
    Optional<User> findByNif(String nif);

    /**
     * Obtiene todos los usuarios.
     * 
     * @return Lista de usuarios.
     */
    List<User> getAllUsers();

    /**
     * Buscar usuarios con filtros aplicados.
     * 
     * @param username Filtro por username.
     * @param email    Filtro por email.
     * @param nif      Filtro por nif.
     * @param role     Filtro por rol.
     * @param pageable Objeto Pageable para la paginación de resultados.
     * @return Página de usuarios que coinciden con los filtros.
     */
    Page<User> findUsersWithFilters(String username, String email, String nif, String role, Pageable pageable);

    /**
     * Actualiza los roles de un usuario.
     * 
     * @param userId ID del usuario.
     * @param roles  Lista de roles a asignar al usuario.
     * @return Usuario actualizado.
     */
    User updateUserRoles(Long userId, List<String> roles);

    /**
     * Elimina un usuario por su ID.
     * 
     * @param userId ID del usuario a eliminar.
     */
    boolean deleteUser(Long userId);

    /**
     * Obtiene los 5 usuarios con más pedidos.
     * 
     * @return Lista de los 5 usuarios con más pedidos.
     */
    List<UserOrdersStatsDTO> getTop5UsersWithMostOrders();
}
