package com.traumaticevolutions.tevosales_backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO request de {@code OrderItem} para representar un ítem dentro de un
 * pedido al recibir una solicitud de creación.
 * 
 * @author Ángel Aragón
 */
@Getter
@Setter
public class OrderItemRequestDTO {

    /**
     * ID del producto que se quiere incluir en el pedido.
     */
    @NotNull(message = "El ID del producto no puede ser nulo")
    private Long productId;

    /**
     * Cantidad de unidades de este producto en el pedido.
     */
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer quantity;
}
