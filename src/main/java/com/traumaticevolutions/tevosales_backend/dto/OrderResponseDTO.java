package com.traumaticevolutions.tevosales_backend.dto;

import com.traumaticevolutions.tevosales_backend.model.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
     * Nombre de usuario del cliente que realizó el pedido.
     */
    private String username;

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
     * Código postal de la dirección de entrega.
     */
    private String postalCode;

    /**
     * Estado actual del pedido determinado por el enum {@link OrderStatus}.
     */
    private OrderStatus status;

    /**
     * Precio total del pedido (calculado).
     */
    private BigDecimal total;

    /**
     * Lista de ítems incluidos en el pedido.
     */
    private List<OrderItemResponseDTO> items;

    /**
     * Fecha y hora en que se creó el pedido.
     */
    private LocalDateTime createdAt;
}
