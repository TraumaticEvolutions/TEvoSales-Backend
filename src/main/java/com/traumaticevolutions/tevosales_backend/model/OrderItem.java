package com.traumaticevolutions.tevosales_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entidad que representa un ítem dentro de un pedido.
 * Asocia un producto con su cantidad en un pedido específico.
 * 
 * @author Ángel Aragón
 */
@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    /**
     * Identificador único del item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Pedido al que pertenece este ítem.
     * Relación {@code @ManyToOne} con {@link Order}: un pedido puede tener
     * varios ítems.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /**
     * Producto asociado a este ítem.
     * Relación {@code @ManyToOne} con {@link Product}: un item esta asociado a un
     * producto.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * Cantidad de unidades del producto en el pedido.
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * Precio total de este ítem (precio unitario * cantidad).
     * Calculado y almacenado en BD como copia.
     */
    @Column(nullable = false)
    private BigDecimal subtotal;
}
