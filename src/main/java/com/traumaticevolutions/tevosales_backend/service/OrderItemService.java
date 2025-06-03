package com.traumaticevolutions.tevosales_backend.service;

import com.traumaticevolutions.tevosales_backend.dto.OrderItemRequestDTO;
import com.traumaticevolutions.tevosales_backend.dto.OrderItemResponseDTO;

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
     * @param orderItemDTO el ítem a guardar
     * @return el ítem guardado como DTO
     */
    OrderItemResponseDTO create(OrderItemRequestDTO orderItemDTO);

    /**
     * Guarda una lista de ítems de pedido.
     *
     * @param orderItemsDTO ítems a guardar
     * @return lista de ítems guardados como DTO
     */
    List<OrderItemResponseDTO> saveAll(List<OrderItemRequestDTO> orderItemsDTO);

    /**
     * Busca los ítems de un pedido específico.
     *
     * @param orderId identificador del pedido
     * @return lista de ítems del pedido como DTO
     */
    List<OrderItemResponseDTO> findByOrderId(Long orderId);
}
