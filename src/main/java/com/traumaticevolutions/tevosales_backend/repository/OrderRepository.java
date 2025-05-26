package com.traumaticevolutions.tevosales_backend.repository;

import com.traumaticevolutions.tevosales_backend.model.Order;
import com.traumaticevolutions.tevosales_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
