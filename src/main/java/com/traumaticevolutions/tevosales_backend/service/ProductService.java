package com.traumaticevolutions.tevosales_backend.service;

import com.traumaticevolutions.tevosales_backend.dto.ProductSalesDTO;
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
     * Obtiene todos los productos disponibles paginados, pudiendo filtrar por
     * nombre y categoría.
     *
     * @param name     nombre del producto a buscar (opcional)
     * @param category categoría del producto a buscar (opcional)
     * @param brand    marca del producto a buscar (opcional)
     * @param pageable objeto que contiene la información de paginación
     * @return lista de productos en formato {@code Page<Product>}
     */
    Page<Product> searchProducts(String name, String category, String brand, Pageable pageable);

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
     * Busca productos con filtros aplicados a nombre, marca y categoría,
     * devolviendo una página de resultados.
     *
     * @param name     nombre del producto (opcional)
     * @param brand    marca del producto (opcional)
     * @param category categoría del producto (opcional)
     * @param page     número de página
     * @param size     tamaño de la página
     * @param sort     criterio de ordenación
     * @return página de productos filtrados
     */
    Page<Product> findAllPaged(String name, String brand, String category, int page, int size, String sort);

    /**
     * Obtiene los 5 productos más vendidos.
     *
     * @return lista de los 5 productos más vendidos
     */
    List<ProductSalesDTO> getTop5BestSellers();
}
