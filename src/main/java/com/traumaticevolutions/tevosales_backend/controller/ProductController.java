package com.traumaticevolutions.tevosales_backend.controller;

import com.traumaticevolutions.tevosales_backend.dto.ProductRequestDTO;
import com.traumaticevolutions.tevosales_backend.dto.ProductResponseDTO;
import com.traumaticevolutions.tevosales_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de productos.
 * Expone endpoints públicos y protegidos según el rol del usuario.
 * 
 * @author Ángel Aragón
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Devuelve todos los productos.
     * Disponible para cualquier usuario autenticado.
     * @return Lista de productos en formato {@code PrdoductResponseDTO}
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    /**
     * Busca productos por nombre.
     * @param name Nombre del producto a buscar
     * @return Lista de productos en formato {@code PrdoductResponseDTO}
     */
    @GetMapping("/search/name")
    public ResponseEntity<List<ProductResponseDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.findByName(name));
    }

    /**
     * Busca productos por categoría (insensible a mayúsculas).
     * @param category Nombre de la categoría a buscar
     * @return Lista de productos en formato {@code PrdoductResponseDTO}
     */
    @GetMapping("/search/category")
    public ResponseEntity<List<ProductResponseDTO>> searchByCategory(@RequestParam String category) {
        return ResponseEntity.ok(productService.findByCategory(category));
    }

    /**
     * Devuelve un producto por su ID.
     * @param id ID del producto.
     * @return Producto en formato {@code ProductResponseDTO}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    /**
     * Crea un nuevo producto.
     * Solo accesible para usuarios con rol ADMIN.
     * @param dto Producto en formato {@code ProductRequestDTO}
     * @return Producto en formato {@code ProductResponseDTO}
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(productService.create(dto));
    }

    /**
     * Actualiza un producto existente.
     * Solo accesible para usuarios con rol ADMIN.
     * @param id Id del producto a actualizar.
     * @param dto Producto en formato {@code ProductRequestDTO}
     * @return Producto en formato {@code ProductResponseDTO}
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id, @RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    /**
     * Elimina un producto por su ID.
     * Solo accesible para usuarios con rol ADMIN.
     * @param id Id del producto a eliminar.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
