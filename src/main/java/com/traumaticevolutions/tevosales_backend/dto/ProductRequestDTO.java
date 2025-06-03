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

    private String imagePath;
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String name;
    @NotBlank(message = "La descripción del producto es obligatoria")
    private String description;
    @NotBlank(message = "El precio del producto es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private BigDecimal price;
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
    @NotBlank(message = "La marca del producto es obligatoria")
    private String brand;
    @NotBlank(message = "La categoría del producto es obligatoria")
    private String category;
    @NotBlank(message = "El estado de actividad del producto es obligatorio")
    private Boolean active;

}
