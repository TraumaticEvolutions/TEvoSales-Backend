package com.traumaticevolutions.tevosales_backend.model.enums;

/**
 * Enum que representa los posibles estados de un pedido en TEvoSales.
 * Permite controlar el flujo del pedido desde su creación hasta la entrega o
 * cancelación.
 * 
 * @author Ángel Aragón
 */
public enum OrderStatus {

    /**
     * El pedido ha sido creado pero aún no procesado.
     */
    PENDING,

    /**
     * El pedido ha sido confirmado y se está preparando.
     */
    CONFIRMED,

    /**
     * El pedido ha sido enviado al cliente.
     */
    SHIPPED,

    /**
     * El pedido ha sido entregado correctamente.
     */
    DELIVERED,

    /**
     * El pedido ha sido cancelado por el cliente o el sistema.
     */
    CANCELLED
}
