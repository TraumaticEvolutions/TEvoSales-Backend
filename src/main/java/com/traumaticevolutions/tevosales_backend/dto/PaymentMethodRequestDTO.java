package com.traumaticevolutions.tevosales_backend.dto;

import com.traumaticevolutions.tevosales_backend.validation.ValidCardNumber;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para registrar un nuevo método de pago del usuario.
 * Actualmente limitado al uso exclusivo de tarjeta bancaria.
 * 
 * Incluye validaciones de formato, longitud y algoritmo de Luhn para el número
 * de tarjeta.
 * 
 * @author Ángel Aragón
 */
@Getter
@Setter
public class PaymentMethodRequestDTO {

    /**
     * Nombre completo del titular de la tarjeta.
     */
    @NotBlank(message = "El nombre del titular es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String holderName;

    /**
     * Número de tarjeta (validado con el algoritmo de Luhn).
     */
    @NotBlank(message = "El número de tarjeta es obligatorio")
    @ValidCardNumber
    private String cardNumber;

    /**
     * Fecha de caducidad (formato MM/AA).
     */
    @NotBlank(message = "La fecha de caducidad es obligatoria")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/\\d{2}$", message = "Formato de fecha inválido. Use MM/AA")
    private String expirationDate;

    /**
     * Código CVV (3 o 4 dígitos).
     */
    @NotBlank(message = "El CVV es obligatorio")
    @Pattern(regexp = "^\\d{3,4}$", message = "El CVV debe tener 3 o 4 dígitos")
    private String cvv;
}
