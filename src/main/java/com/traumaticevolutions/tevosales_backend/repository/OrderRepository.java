package com.traumaticevolutions.tevosales_backend.repository;

import com.traumaticevolutions.tevosales_backend.model.Order;
import com.traumaticevolutions.tevosales_backend.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad {@link Order}.
 * Permite realizar operaciones CRUD y consultas personalizadas sobre pedidos.
 * 
 * @author Ángel Aragón
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Obtiene todos los pedidos asociados a un usuario específico.
     *
     * @param user el usuario autenticado
     * @return lista de pedidos del usuario
     */
    List<Order> findByUser(User user);

    /**
     * Obtiene todos los pedidos asociados a un usuario específico de forma
     * paginada.
     *
     * @param user     el usuario autenticado
     * @param pageable información de paginación
     * @return página de pedidos del usuario
     */
    Page<Order> findByUser(User user, Pageable pageable);

    /**
     * Busca un pedido por su ID y el usuario asociado.
     *
     * @param id   el identificador del pedido
     * @param user el usuario autenticado
     * @return el pedido encontrado, o null si no existe o no pertenece al usuario
     */
    Optional<Order> findByIdAndUser(Long id, User user);

    /**
     * Elimina un pedido por su ID y el usuario asociado.
     *
     * @param id   el identificador del pedido
     * @param user el usuario autenticado
     * @return true si se eliminó correctamente, false si no existía o no pertenecía
     *         al usuario
     */
    void deleteByIdAndUser(Long id, User user);

    /**
     * Obtiene todos los pedidos asociados a un usuario específico dentro de un
     * rango
     * de fechas de forma paginada.
     *
     * @param user     el usuario autenticado
     * @param start    la fecha y hora de inicio del rango
     * @param end      la fecha y hora de fin del rango
     * @param pageable información de paginación
     * @return página de pedidos del usuario dentro del rango de fechas
     */
    Page<Order> findByUserAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end, Pageable pageable);

    /**
     * Obtiene todos los pedidos asociados a un usuario específico que fueron
     * creados
     * después de una fecha y hora específicas.
     *
     * @param user     el usuario autenticado
     * @param start    la fecha y hora de inicio del rango
     * @param pageable información de paginación
     * @return página de pedidos del usuario creados después de la fecha y hora
     */
    Page<Order> findByUserAndCreatedAtAfter(User user, LocalDateTime start, Pageable pageable);

    /**
     * Obtiene todos los pedidos asociados a un usuario específico que fueron
     * creados
     * antes de una fecha y hora específicas.
     *
     * @param user     el usuario autenticado
     * @param end      la fecha y hora de fin del rango
     * @param pageable información de paginación
     * @return página de pedidos del usuario creados antes de la fecha y hora
     */
    Page<Order> findByUserAndCreatedAtBefore(User user, LocalDateTime end, Pageable pageable);
}
