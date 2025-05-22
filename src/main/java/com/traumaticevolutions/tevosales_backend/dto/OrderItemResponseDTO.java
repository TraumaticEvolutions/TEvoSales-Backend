package com.traumaticevolutions.tevosales_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO para representar un ítem de un {@code Order} al devolver la información
 * al cliente.
 * Incluye nombre del producto, cantidad solicitada y subtotal.
 * 
 * @author Ángel Aragón
 */
@Getter
@Setter
public class OrderItemResponseDTO {

    /**
     * ID del producto asociado a este ítem.
     */
    private Long productId;

    /**
     * Nombre del producto.
     */
    private String productName;

    /**
     * Cantidad de unidades del producto en este ítem.
     */
    private Integer quantity;

    /**
     * Precio unitario del producto (en el momento del pedido).
     */
    private BigDecimal unitPrice;

    /**
     * Subtotal (unitPrice * quantity).
     */
    private BigDecimal subtotal;
}
