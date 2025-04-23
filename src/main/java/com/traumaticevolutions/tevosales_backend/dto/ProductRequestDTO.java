package com.traumaticevolutions.tevosales_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO para recibir los datos de creación o actualización de un producto.
 * 
 * @author Ángel Aragón
 */
@Getter
@Setter
public class ProductRequestDTO {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String brand;
    private String category;
    private byte[] image;
    private Boolean active;
}
