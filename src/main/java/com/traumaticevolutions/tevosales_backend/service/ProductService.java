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

    /**
     * Obtiene todos los productos.
     *
     * @return lista de productos
     */
    List<Product> findAll();

    /**
     * Busca un producto por su ID.
     *
     * @param id el identificador del producto
     * @return Optional con el producto encontrado (si existe)
     */
    Optional<Product> findById(Long id);

    /**
     * Crea un nuevo producto.
     *
     * @param product el producto a crear
     * @return Optional con el producto creado (si se creó correctamente)
     */
    Optional<Product> create(Product product);

    /**
     * Actualiza un producto existente.
     *
     * @param id      el identificador del producto a actualizar
     * @param product los nuevos datos del producto
     * @return Optional con el producto actualizado (si se actualizó correctamente)
     */
    Optional<Product> update(Long id, Product product);

    /**
     * Elimina un producto por su ID.
     *
     * @param id el identificador del producto a eliminar
     */
    void delete(Long id);

    /**
     * Busca productos por su nombre.
     *
     * @param name el nombre del producto
     * @return lista de productos que coinciden con el nombre
     */
    List<Product> findByName(String name);

    /**
     * Busca productos por su categoría.
     *
     * @param category la categoría del producto
     * @return lista de productos que coinciden con la categoría
     */
    List<Product> findByCategory(String category);

    /**
     * Obtiene todos los productos de forma paginada.
     *
     * @param pageable información de paginación
     * @return página de productos
     */
    Page<Product> findAllPaged(Pageable pageable);
}
