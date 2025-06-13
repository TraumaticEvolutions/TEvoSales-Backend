package com.traumaticevolutions.tevosales_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.traumaticevolutions.tevosales_backend.model.enums.OrderStatus;

/**
 * DTO request de {@code Order} para representar los datos de un nuevo pedido.
 * Incluye la dirección de envío y los productos solicitados.
 * 
 * @author Ángel Aragón
 */
@Getter
@Setter
public class OrderRequestDTO {

    /**
     * Dirección de envío del pedido.
     * No puede estar vacía.
     */
    @NotBlank(message = "La dirección es obligatoria")
    private String address;

    /**
     * Número del portal o edificio.
     * No puede estar vacío.
     */
    @NotBlank(message = "El número es obligatorio")
    private String number;

    /**
     * Planta o piso del domicilio.
     * Puede ser nulo si no se proporciona.
     */
    private String floor;

    /**
     * Código postal del domicilio.
     * No puede estar vacío.
     */
    @NotBlank(message = "El código postal es obligatorio")
    @Length(max = 10, message = "El código postal no puede exceder los 10 caracteres")
    private String postalCode;

    /**
     * Lista de productos incluidos en el pedido.
     * Debe contener al menos un producto.
     */
    @NotNull(message = "Debe incluir al menos un producto en el pedido")
    private List<OrderItemRequestDTO> items;

    /**
     * Estado del pedido.
     * No puede ser nulo.
     */
    @NotNull(message = "El estado del pedido es obligatorio")
    private OrderStatus status;
}
