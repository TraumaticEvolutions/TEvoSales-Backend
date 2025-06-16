package com.traumaticevolutions.tevosales_backend.repository;

import com.traumaticevolutions.tevosales_backend.dto.UserOrdersStatsDTO;
import com.traumaticevolutions.tevosales_backend.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    /**
     * Obtiene los 5 usuarios con más pedidos realizados.
     * 
     * @return Lista de DTOs con el nombre de usuario y la cantidad de pedidos.
     */
    @Query("SELECT new com.traumaticevolutions.tevosales_backend.dto.UserOrdersStatsDTO(u.username, COUNT(o)) " +
            "FROM User u JOIN Order o ON o.user = u " +
            "GROUP BY u.id, u.username " +
            "ORDER BY COUNT(o) DESC")
    List<UserOrdersStatsDTO> findTop5UsersWithMostOrders();
}
