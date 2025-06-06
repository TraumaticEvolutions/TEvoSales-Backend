package com.traumaticevolutions.tevosales_backend.service;

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
     * @return el ítem guardado como DTO
     */
    OrderItem create(OrderItem orderItem);

    /**
     * Guarda una lista de ítems de pedido.
     *
     * @param orderItemsDTO ítems a guardar
     * @return lista de ítems guardados como DTO
     */
    List<OrderItem> saveAll(List<OrderItem> orderItems);

    /**
     * Busca los ítems de un pedido específico.
     *
     * @param orderId identificador del pedido
     * @return lista de ítems del pedido como DTO
     */
    List<OrderItem> findByOrderId(Long orderId);

    /**
     * Elimina todos los ítems asociados a un pedido.
     *
     * @param orderId identificador del pedido cuyos ítems se eliminarán
     */
    void deleteByOrderId(Long orderId);
}
