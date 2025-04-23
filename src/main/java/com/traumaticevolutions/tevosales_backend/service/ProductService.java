package com.traumaticevolutions.tevosales_backend.service;

import com.traumaticevolutions.tevosales_backend.dto.ProductRequestDTO;
import com.traumaticevolutions.tevosales_backend.dto.ProductResponseDTO;

import java.util.List;

/**
 * Interfaz del servicio para gestionar productos.
 * Define operaciones CRUD y búsquedas básicas.
 * 
 * @author Ángel Aragón
 */
public interface ProductService {

    List<ProductResponseDTO> findAll();
    ProductResponseDTO findById(Long id);
    ProductResponseDTO create(ProductRequestDTO dto);
    ProductResponseDTO update(Long id, ProductRequestDTO dto);
    void delete(Long id);
    List<ProductResponseDTO> findByName(String name);
    List<ProductResponseDTO> findByCategory(String category);
}
