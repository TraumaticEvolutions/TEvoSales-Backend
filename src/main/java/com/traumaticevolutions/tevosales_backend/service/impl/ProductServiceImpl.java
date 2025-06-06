package com.traumaticevolutions.tevosales_backend.service.impl;

import com.traumaticevolutions.tevosales_backend.model.Product;
import com.traumaticevolutions.tevosales_backend.repository.ProductRepository;
import com.traumaticevolutions.tevosales_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Implementación del servicio de productos.
 * Gestiona la lógica de negocio para operaciones CRUD y búsqueda por nombre o
 * categoría.
 * 
 * @author Ángel Aragón
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /**
     * Obtiene todos los productos disponibles.
     * 
     * @return lista de productos en formato {@code List<Product>}
     */
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();

    }

    /**
     * Busca un producto por su ID.
     *
     * @param id identificador del producto
     * @return producto encontrado en formato {@code Optional<Product>}
     */
    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Crea un nuevo producto a partir de los datos proporcionados.
     * 
     * @param dto objeto con los datos del producto a crear
     * @return producto creado en formato {@code Optional<Product>}
     */
    @Override
    public Optional<Product> create(Product product) {
        return Optional.of(productRepository.save(product));
    }

    /**
     * Actualiza un producto existente con los nuevos datos proporcionados.
     *
     * @param id   identificador del producto a actualizar
     * @param newP objeto con los nuevos datos del producto
     * @return producto actualizado en formato {@code Optional<Product>}
     * @throws NoSuchElementException si el producto no existe
     */
    @Override
    public Optional<Product> update(Long id, Product newP) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
        existing.setName(newP.getName());
        existing.setDescription(newP.getDescription());
        existing.setPrice(newP.getPrice());
        existing.setCategory(newP.getCategory());
        existing.setImagePath(newP.getImagePath());
        existing.setStock(newP.getStock());
        existing.setActive(newP.getActive());
        return Optional.of(productRepository.save(existing));
    }

    /**
     * Elimina un producto por su ID.
     *
     * @param id identificador del producto a eliminar
     * @throws NoSuchElementException si el producto no existe
     */
    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Producto no encontrado");
        }
        productRepository.deleteById(id);
    }

    /**
     * Busca productos por su nombre (sin distinguir mayúsculas).
     *
     * @param name nombre exacto a buscar
     * @return lista de productos con ese nombre en formato {@code List<Product>}
     */
    @Override
    public List<Product> findByName(String name) {
        List<Product> products = productRepository.findByNameIgnoreCase(name);
        return products;
    }

    /**
     * Busca productos por su categoría (sin distinguir mayúsculas).
     * 
     * @param category nombre de la categoría a buscar
     * @return lista de productos en esa categoría en formato {@code List<Product>}
     */
    @Override
    public List<Product> findByCategory(String category) {
        List<Product> products = productRepository.findByCategoryIgnoreCase(category);
        return products;
    }

}
