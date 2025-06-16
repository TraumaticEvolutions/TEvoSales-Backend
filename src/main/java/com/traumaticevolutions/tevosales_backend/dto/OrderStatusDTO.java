package com.traumaticevolutions.tevosales_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * DTO para representar el estado de un pedido.
 * Contiene el estado del pedido como una cadena.
 * 
 * @author Ángel Aragón
 */
public class OrderStatusDTO {
    private String status;
}