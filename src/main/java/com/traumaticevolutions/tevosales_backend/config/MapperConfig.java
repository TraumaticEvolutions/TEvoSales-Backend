package com.traumaticevolutions.tevosales_backend.config;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.traumaticevolutions.tevosales_backend.dto.OrderItemResponseDTO;
import com.traumaticevolutions.tevosales_backend.dto.OrderResponseDTO;
import com.traumaticevolutions.tevosales_backend.dto.UserResponseAdminDTO;
import com.traumaticevolutions.tevosales_backend.model.OrderItem;
import com.traumaticevolutions.tevosales_backend.model.Role;
import com.traumaticevolutions.tevosales_backend.model.User;
import com.traumaticevolutions.tevosales_backend.model.Order;

import lombok.RequiredArgsConstructor;

/**
 * Clase de configuración global para el bean de ModelMapper.
 * Permite inyectar ModelMapper en cualquier componente de la aplicación.
 * 
 * @author Ángel Aragón
 */
@RequiredArgsConstructor
@Configuration
public class MapperConfig {

    /**
     * Bean que instancia y expone el ModelMapper.
     * Este objeto se utilizará para mapear entre entidades y DTOs.
     *
     * @return ModelMapper configurado.
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<OrderItem, OrderItemResponseDTO>() {
            @Override
            protected void configure() {
                map().setImagePath(source.getProduct().getImagePath());
            }
        });

        modelMapper.addMappings(new PropertyMap<User, UserResponseAdminDTO>() {
            @Override
            protected void configure() {
                using(ctx -> ((User) ctx.getSource()).getOrders() != null ? ((User) ctx.getSource()).getOrders().size()
                        : 0)
                        .map(source, destination.getOrdersCount());

                using(ctx -> ((User) ctx.getSource()).getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toList()))
                        .map(source, destination.getRoles());
            }
        });

        modelMapper.addMappings(new PropertyMap<Order, OrderResponseDTO>() {
            @Override
            protected void configure() {

                using(ctx -> {
                    Order order = (Order) ctx.getSource();
                    return (order != null && order.getUser() != null) ? order.getUser().getUsername() : null;
                }).map(source, destination.getUsername());

                using(ctx -> {
                    Order order = (Order) ctx.getSource();
                    return (order != null && order.getOrderItems() != null)
                            ? order.getOrderItems().stream()
                                    .map(item -> modelMapper.map(item, OrderItemResponseDTO.class))
                                    .collect(Collectors.toList())
                            : null;
                }).map(source, destination.getItems());
            }
        });
        return modelMapper;
    }
}
