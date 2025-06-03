package com.traumaticevolutions.tevosales_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO para enviar al frontend los datos públicos de un producto.
 * 
 * @author Ángel Aragón
 */
@Getter
@Setter
public class ProductResponseDTO {

    /**
     * ID único del producto.
     */
    private Long id;
    /**
     * Nombre del producto.
     */
    private String name;
    /**
     * Descripción del producto.
     */
    private String description;
    /**
     * Precio unitario del producto.
     */
    private BigDecimal price;
    /**
     * Unidades disponibles en stock.
     */
    private Integer stock;
    /**
     * Marca del producto.
     */
    private String brand;
    /**
     * Categoría del producto.
     */
    private String category;
    /**
     * Indica si el producto está activo y disponible para la venta.
     */
    private Boolean active;
    /**
     * Ruta de la imagen del producto.
     * Puede ser una URL o una ruta relativa al servidor.
     */
    private String imagePath;
}
