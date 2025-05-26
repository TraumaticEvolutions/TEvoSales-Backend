package com.traumaticevolutions.tevosales_backend.service;

import com.traumaticevolutions.tevosales_backend.dto.OrderRequestDTO;
import com.traumaticevolutions.tevosales_backend.dto.OrderResponseDTO;

import java.util.List;

/**
 * Servicio que define las operaciones disponibles para gestionar pedidos.
 * 
 * @author Ángel Aragón
 */
public interface OrderService {

    /**
     * Crea un nuevo pedido para el usuario autenticado.
     *
     * @param orderRequestDTO datos del pedido
     * @return pedido creado
     */
    OrderResponseDTO create(OrderRequestDTO orderRequestDTO);

    /**
     * Obtiene todos los pedidos del usuario autenticado.
     *
     * @return lista de pedidos
     */
    List<OrderResponseDTO> findByAuthenticatedUser();

    /**
     * Elimina un pedido por su ID si pertenece al usuario autenticado.
     *
     * @param orderId identificador del pedido
     */
    void delete(Long orderId);
}
