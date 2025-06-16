package com.traumaticevolutions.tevosales_backend.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para representar el estado de un pedido.
 * Contiene el estado del pedido como una cadena.
 * 
 * @author Ángel Aragón
 */
@Getter
@Setter
public class OrderStatusDTO {
    /**
     * Estado del pedido.
     */
    private String status;
}