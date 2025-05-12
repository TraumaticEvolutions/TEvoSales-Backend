package com.traumaticevolutions.tevosales_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO de respuesta para mostrar información básica del método de pago
 * del usuario sin comprometer datos sensibles.
 * 
 * @author Ángel Aragón
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodResponseDTO {

    /**
     * Identificador único del método de pago.
     */
    private Long id;

    /**
     * Nombre del titular de la tarjeta.
     */
    private String cardHolderName;

    /**
     * Últimos 4 dígitos del número de tarjeta.
     */
    private String last4Digits;

    /**
     * Fecha de caducidad.
     */
    private String expirationDate;

    /**
     * Tipo de tarjeta.
     * Puede obtenerse opcionalmente si se implementa detección por BIN.
     */
    private String cardType;
}
