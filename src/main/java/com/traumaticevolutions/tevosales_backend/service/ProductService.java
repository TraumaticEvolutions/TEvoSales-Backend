package com.traumaticevolutions.tevosales_backend.service;

import com.traumaticevolutions.tevosales_backend.model.Product;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interfaz del servicio para gestionar productos.
 * Define operaciones CRUD y búsquedas básicas.
 * 
 * @author Ángel Aragón
 */
public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Optional<Product> create(Product product);

    Optional<Product> update(Long id, Product product);

    void delete(Long id);

    List<Product> findByName(String name);

    List<Product> findByCategory(String category);

    Page<Product> findAllPaged(Pageable pageable);
}
