package com.traumaticevolutions.tevosales_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO request de {@code Order} para representar los datos de un nuevo pedido.
 * Incluye la dirección de envío y los productos solicitados.
 * 
 * @author Ángel Aragón
 */
@Getter
@Setter
public class OrderRequestDTO {

    @NotBlank(message = "La dirección es obligatoria")
    private String address;

    @NotBlank(message = "El número es obligatorio")
    private String number;

    private String floor;

    @NotNull(message = "Debe incluir al menos un producto en el pedido")
    private List<OrderItemRequestDTO> items;
}
