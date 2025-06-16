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
     * URL de la imagen del producto
     */
    @Column(name = "image")
    private String imagePath;

}
