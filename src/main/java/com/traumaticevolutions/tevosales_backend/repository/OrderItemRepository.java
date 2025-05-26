package com.traumaticevolutions.tevosales_backend.repository;

import com.traumaticevolutions.tevosales_backend.model.OrderItem;
import com.traumaticevolutions.tevosales_backend.model.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad {@link OrderItem}.
 * Permite realizar operaciones CRUD sobre los ítems de pedido.
 * 
 * @author Ángel Aragón
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    /**
     * Busca los ítems de un pedido específico.
     *
     * @param order el pedido
     * @return lista de ítems del pedido
     */
    List<OrderItem> findByOrder(Order order);
}
