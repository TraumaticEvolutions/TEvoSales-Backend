package com.traumaticevolutions.tevosales_backend.service.impl;

import com.traumaticevolutions.tevosales_backend.model.Product;
import com.traumaticevolutions.tevosales_backend.repository.ProductRepository;
import com.traumaticevolutions.tevosales_backend.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
     * Obtiene todos los productos disponibles, pudiendo filtrar por nombre y
     * categoría.
     *
     * @param name     nombre del producto a buscar (opcional)
     * @param category categoría del producto a buscar (opcional)
     * @param pageable objeto que contiene la información de paginación
     * @return lista de productos en formato {@code Page<Product>}
     */
    @Override
    public Page<Product> searchProducts(String name, String category, String brand, Pageable pageable) {
        Specification<Product> spec = Specification.where(null);

        if (name != null && !name.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }
        if (category != null && !category.isBlank()) {
            spec = spec.and(
                    (root, query, cb) -> cb.like(cb.lower(root.get("category")), "%" + category.toLowerCase() + "%"));
        }
        if (brand != null && !brand.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%"));
        }

        return productRepository.findAll(spec, pageable);
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

        if (newP.getName() != null && !newP.getName().isBlank()) {
            existing.setName(newP.getName());
        }
        if (newP.getDescription() != null && !newP.getDescription().isBlank()) {
            existing.setDescription(newP.getDescription());
        }
        if (newP.getPrice() != null) {
            existing.setPrice(newP.getPrice());
        }
        if (newP.getCategory() != null && !newP.getCategory().isBlank()) {
            existing.setCategory(newP.getCategory());
        }
        if (newP.getImagePath() != null && !newP.getImagePath().isBlank()) {
            existing.setImagePath(newP.getImagePath());
        }
        if (newP.getStock() != null) {
            existing.setStock(newP.getStock());
        }

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
    @Override
    public Page<Product> findAllPaged(String name, String brand, String category, int page, int size, String sort) {
        String[] sortParts = sort.split(",");
        Sort sortObj;
        if (sortParts.length == 2) {
            sortObj = Sort.by(new Sort.Order(Sort.Direction.fromString(sortParts[1]), sortParts[0]));
        } else if (sortParts.length == 1 && !sortParts[0].isBlank()) {
            sortObj = Sort.by(sortParts[0]).ascending();
        } else {
            sortObj = Sort.by("id").ascending();
        }
        Pageable pageable = PageRequest.of(page, size, sortObj);
        return searchProducts(name, category, brand, pageable);
    }

}
