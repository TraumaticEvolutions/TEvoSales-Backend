package com.traumaticevolutions.tevosales_backend.service;

import com.traumaticevolutions.tevosales_backend.dto.ProductRequestDTO;
import com.traumaticevolutions.tevosales_backend.model.Product;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interfaz del servicio para gestionar productos.
 * Define operaciones CRUD y búsquedas básicas.
 * 
 * @author Ángel Aragón
 */
public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Optional<Product> create(ProductRequestDTO dto);

    Optional<Product> update(Long id, ProductRequestDTO dto);

    void delete(Long id);

    List<Product> findByName(String name);

    List<Product> findByCategory(String category);
}
