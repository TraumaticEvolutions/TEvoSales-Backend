package com.traumaticevolutions.tevosales_backend.model;

import com.traumaticevolutions.tevosales_backend.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidad que representa un pedido realizado por un usuario.
 * Incluye dirección de entrega, estado del pedido y relación con productos.
 * 
 * @author Ángel Aragón
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    /**
     * Identificador único del pedido.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Usuario que ha realizado el pedido.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Dirección donde se entregará el pedido.
     */
    @Column(nullable = false)
    private String address;

    /**
     * Número del domicilio.
     */
    @Column(nullable = false)
    private String number;

    /**
     * Planta o piso del domicilio (opcional).
     */
    private String floor;

    /**
     * Estado actual del pedido.
     * Numerado como {@link OrderStatus}:
     * <ul>
     * <li>PENDING: Pedido pendiente de procesar.</li>
     * <li>PROCESSING: Pedido en proceso.</li>
     * <li>SHIPPED: Pedido enviado.</li>
     * <li>DELIVERED: Pedido entregado.</li>
     * <li>CANCELLED: Pedido cancelado.</li>
     * </ul>
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    /**
     * Precio total del pedido. Se calcula en base a los productos y sus cantidades.
     */
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    /**
     * Relación con los productos del pedido, a través de la tabla intermedia
     * OrderItem.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

}
