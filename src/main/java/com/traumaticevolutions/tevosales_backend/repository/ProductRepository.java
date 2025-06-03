package com.traumaticevolutions.tevosales_backend.repository;

import com.traumaticevolutions.tevosales_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la gestión de productos.
 * Permite realizar operaciones CRUD sobre la entidad {@code Product}.
 * 
 * @author Ángel Aragón
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Busca productos que contengan una parte del nombre.
     * 
     * @param name parte del nombre a buscar
     * @return lista de productos cuyo nombre contiene el texto indicado
     */
    List<Product> findByNameIgnoreCase(String name);

    /**
     * Busca productos por categoría.
     * 
     * @param category nombre de la categoría
     * @return lista de productos de esa categoría
     */
    List<Product> findByCategoryIgnoreCase(String category);
}
