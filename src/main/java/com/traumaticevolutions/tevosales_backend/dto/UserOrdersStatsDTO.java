package com.traumaticevolutions.tevosales_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO para estadísticas de usuarios y cantidad de pedidos realizados.
 * 
 * @author Ángel Aragón
 */
@Getter
@AllArgsConstructor
public class UserOrdersStatsDTO {
    private String username;
    private Long ordersCount;
}