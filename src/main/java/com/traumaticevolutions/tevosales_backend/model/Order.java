package com.traumaticevolutions.tevosales_backend.model;

import com.traumaticevolutions.tevosales_backend.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
     * Código postal del domicilio.
     * Puede ser nulo si no se proporciona.
     */
    private String postalCode;

    /**
     * Estado actual del pedido.
     * Numerado como {@link OrderStatus}:
     * <ul>
     * <li>PENDIENTE: Pedido pendiente de procesar.</li>
     * <li>CONFIRMADO: Pedido confirmado.</li>
     * <li>ENVIADO: Pedido enviado.</li>
     * <li>ENTREGADO: Pedido entregado.</li>
     * <li>CANCELADO: Pedido cancelado.</li>
     * </ul>
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDIENTE;

    /**
     * Precio total del pedido. Se calcula en base a los productos y sus cantidades.
     */
    @Column(name = "total_price", nullable = false)
    private BigDecimal total;

    /**
     * Fecha y hora de creación del pedido.
     */
    @Column(nullable = true, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Relación con los productos del pedido, a través de la tabla intermedia
     * OrderItem.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

}
