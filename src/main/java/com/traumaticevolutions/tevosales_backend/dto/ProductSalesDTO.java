package com.traumaticevolutions.tevosales_backend.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * DTO para estadísticas de ventas de productos.
 */
@Getter
@AllArgsConstructor
public class ProductSalesDTO {
    private String name;
    private Long quantity;

}