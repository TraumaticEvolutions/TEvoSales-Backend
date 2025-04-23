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

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String brand;
    private String category;
    private Boolean active;
}
