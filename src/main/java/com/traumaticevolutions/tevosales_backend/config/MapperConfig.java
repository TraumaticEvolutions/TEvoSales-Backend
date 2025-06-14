package com.traumaticevolutions.tevosales_backend.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.traumaticevolutions.tevosales_backend.dto.OrderItemResponseDTO;
import com.traumaticevolutions.tevosales_backend.dto.UserResponseDTO;
import com.traumaticevolutions.tevosales_backend.model.OrderItem;
import com.traumaticevolutions.tevosales_backend.model.User;

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

        modelMapper.addMappings(new PropertyMap<User, UserResponseDTO>() {
            @Override
            protected void configure() {
                using(ctx -> ((User) ctx.getSource()).getOrders() != null ? ((User) ctx.getSource()).getOrders().size() : 0)
                    .map(source, destination.getOrdersCount());
            }
        });

        return modelMapper;
    }
}
