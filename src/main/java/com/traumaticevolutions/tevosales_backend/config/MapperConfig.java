package com.traumaticevolutions.tevosales_backend.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.traumaticevolutions.tevosales_backend.dto.OrderItemResponseDTO;
import com.traumaticevolutions.tevosales_backend.model.OrderItem;

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

        // Mapeo personalizado para OrderItem -> OrderItemResponseDTO
        modelMapper.addMappings(new PropertyMap<OrderItem, OrderItemResponseDTO>() {
            @Override
            protected void configure() {
                map().setImagePath(source.getProduct().getImagePath());
            }
        });

        return modelMapper;
    }
}
