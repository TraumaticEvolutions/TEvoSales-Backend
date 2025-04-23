package com.traumaticevolutions.tevosales_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entidad que representa un producto del marketplace de TEvoSales.
 * Almacena datos comerciales, imagen binaria y la relación con su proveedor.
 * 
 * @author Ángel Aragón
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {

    /**
     * Identificador único del producto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre comercial del producto.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Descripción detallada del producto.
     */
    @Column(length = 2000)
    private String description;

    /**
     * Precio unitario del producto.
     */
    @Column(nullable = false)
    private BigDecimal price;

    /**
     * Unidades disponibles en stock.
     */
    @Column(nullable = false)
    private Integer stock;

    /**
     * Marca o fabricante del producto.
     */
    private String brand;

    /**
     * Categoría del producto (uso provisional como String).
     */
    private String category;

    /**
     * Imagen binaria del producto (almacenada como LONGBLOB).
     */
    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    /**
     * Indica si el producto está activo (visible para clientes).
     */
    @Column(nullable = false)
    private Boolean active = true;

    /**
     * TODO: Pendiente de crear modelo Provider
     * Proveedor al que pertenece este producto.
     * Relación muchos a uno: muchos productos pueden tener el mismo proveedor.
     
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Provider provider;*/

    /**
    * TODO: Pendiente de crear modelo Discount 
    * Lista de descuentos aplicables a este producto.
    * Relación muchos a muchos con la entidad {@code Discount}.
    * 
    */
    // @ManyToMany(mappedBy = "products")
    // private Set<Discount> discounts = new HashSet<>();
}
