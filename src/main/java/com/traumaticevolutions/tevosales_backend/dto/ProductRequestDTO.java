package com.traumaticevolutions.tevosales_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para recibir los datos de creación o actualización de un producto.
 * 
 * @author Ángel Aragón
 */
@Getter
@Setter
public class ProductRequestDTO {

    /**
     * Ruta de la imagen del producto.
     * Puede ser una URL o una ruta local.
     */
    private String imagePath;

    /**
     * Nombre del producto.
     * Debe ser único y no puede estar vacío.
     */
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String name;

    /**
     * Descripción del producto.
     * No puede estar vacío.
     */
    @NotBlank(message = "La descripción del producto es obligatoria")
    private String description;

    /**
     * Precio del producto.
     * No puede ser negativo.
     */
    @NotBlank(message = "El precio del producto es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private BigDecimal price;

    /**
     * Stock del producto.
     * No puede ser negativo.
     */
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    /**
     * Marca del producto.
     * No puede estar vacío.
     */
    @NotBlank(message = "La marca del producto es obligatoria")
    private String brand;

    /**
     * Categoría del producto.
     * No puede estar vacío.
     */
    @NotBlank(message = "La categoría del producto es obligatoria")
    private String category;

    /**
     * Estado de actividad del producto.
     * No puede ser nulo.
     */
    @NotBlank(message = "El estado de actividad del producto es obligatorio")
    private Boolean active;

}
