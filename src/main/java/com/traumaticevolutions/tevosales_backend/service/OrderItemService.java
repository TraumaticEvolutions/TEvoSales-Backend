package com.traumaticevolutions.tevosales_backend.service;

import com.traumaticevolutions.tevosales_backend.model.Order;
import com.traumaticevolutions.tevosales_backend.model.OrderItem;

import java.util.List;

/**
 * Servicio que define operaciones para gestionar los ítems de pedido.
 * No se expone al cliente, es usado internamente por el servicio de pedidos.
 * 
 * @author Ángel Aragón
 */
public interface OrderItemService {

    /**
     * Crea un nuevo ítem de pedido.
     *
     * @param orderItem el ítem a guardar
     * @return el ítem guardado
     */
    OrderItem create(OrderItem orderItem);

    /**
     * Guarda una lista de ítems de pedido.
     *
     * @param orderItems ítems a guardar
     * @return lista de ítems guardados
     */
    List<OrderItem> saveAll(List<OrderItem> orderItems);

    /**
     * Busca los ítems de un pedido específico.
     *
     * @param order Order del cual se buscan los ítems
     * @return lista de ítems del pedido
     */

    List<OrderItem> findByOrder(Order order);
}
