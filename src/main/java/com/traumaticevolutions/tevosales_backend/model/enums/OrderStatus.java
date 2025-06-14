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
    PENDIENTE,

    /**
     * El pedido ha sido confirmado y se está preparando.
     */
    CONFIRMADO,

    /**
     * El pedido ha sido enviado al cliente.
     */
    ENVIADO,

    /**
     * El pedido ha sido entregado correctamente.
     */
    ENTREGADO,

    /**
     * El pedido ha sido cancelado por el cliente o el sistema.
     */
    CANCELADO
}
