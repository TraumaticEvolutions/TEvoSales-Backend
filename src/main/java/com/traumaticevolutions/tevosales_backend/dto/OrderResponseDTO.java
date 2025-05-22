package com.traumaticevolutions.tevosales_backend.dto;

import com.traumaticevolutions.tevosales_backend.model.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para representar un {@code Order} completo en la respuesta.
 * Incluye dirección, estado, precio total e ítems asociados.
 * 
 * @author Ángel Aragón
 */
@Getter
@Setter
public class OrderResponseDTO {

    /**
     * ID del pedido.
     */
    private Long id;

    /**
     * Dirección de envío del pedido.
     */
    private String address;

    /**
     * Número del portal o edificio.
     */
    private String number;

    /**
     * Planta o piso, si aplica.
     */
    private String floor;

    /**
     * Estado actual del pedido determinado por el enum {@link OrderStatus}.
     */
    private OrderStatus status;

    /**
     * Precio total del pedido (calculado).
     */
    private BigDecimal totalPrice;

    /**
     * Lista de ítems incluidos en el pedido.
     */
    private List<OrderItemResponseDTO> items;
}
