package com.traumaticevolutions.tevosales_backend.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        return new ModelMapper();
    }
}
