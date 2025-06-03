package com.traumaticevolutions.tevosales_backend.repository;

import com.traumaticevolutions.tevosales_backend.dto.OrderResponseDTO;
import com.traumaticevolutions.tevosales_backend.model.Order;
import com.traumaticevolutions.tevosales_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

}
