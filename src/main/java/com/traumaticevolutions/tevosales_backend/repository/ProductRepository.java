package com.traumaticevolutions.tevosales_backend.repository;

import com.traumaticevolutions.tevosales_backend.dto.ProductSalesDTO;
import com.traumaticevolutions.tevosales_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la gestión de productos.
 * Permite realizar operaciones CRUD sobre la entidad {@code Product}.
 * 
 * @author Ángel Aragón
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

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

    /**
     * Obtiene los 5 productos más vendidos.
     * 
     * @return lista de los 5 productos más vendidos con su nombre y cantidad
     *         vendida
     */
    @Query("SELECT new com.traumaticevolutions.tevosales_backend.dto.ProductSalesDTO(p.name, SUM(oi.quantity)) " +
            "FROM OrderItem oi JOIN oi.product p " +
            "GROUP BY p.id, p.name " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<ProductSalesDTO> findTop5BestSellers();
}
