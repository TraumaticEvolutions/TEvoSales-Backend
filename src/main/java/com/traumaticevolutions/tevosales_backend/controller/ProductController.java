package com.traumaticevolutions.tevosales_backend.controller;

import com.traumaticevolutions.tevosales_backend.dto.ProductRequestDTO;
import com.traumaticevolutions.tevosales_backend.dto.ProductResponseDTO;
import com.traumaticevolutions.tevosales_backend.model.Product;
import com.traumaticevolutions.tevosales_backend.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

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
    private final ModelMapper modelMapper;

    /**
     * Devuelve todos los productos.
     * Disponible para cualquier usuario autenticado.
     * 
     * @return Lista de productos en formato {@code ProductResponseDTO}
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll() {
        return ResponseEntity.ok(productService.findAll().stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .toList());
    }

    /**
     * Busca productos por nombre.
     * 
     * @param name Nombre del producto a buscar
     * @return Lista de productos en formato {@code ProductResponseDTO}
     */
    @GetMapping("/search/name")
    public ResponseEntity<List<ProductResponseDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.findByName(name).stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .toList());
    }

    /**
     * Busca productos por categoría (insensible a mayúsculas).
     * 
     * @param category Nombre de la categoría a buscar
     * @return Lista de productos en formato {@code ProductResponseDTO}
     */
    @GetMapping("/search/category")
    public ResponseEntity<List<ProductResponseDTO>> searchByCategory(@RequestParam String category) {
        return ResponseEntity.ok(productService.findByCategory(category).stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .toList());
    }

    /**
     * Devuelve un producto por su ID.
     * 
     * @param id ID del producto.
     * @return Producto en formato {@code ProductResponseDTO}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id)
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado")));
    }

    /**
     * Crea un nuevo producto.
     * Solo accesible para usuarios con rol ADMIN.
     * 
     * @param dto   Producto en formato {@code ProductRequestDTO}
     * @param image Imagen del producto (opcional)
     * @return Producto creado en formato {@code ProductResponseDTO}
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO dto) {
        try {
            Product product = productService.create(modelMapper.map(dto, Product.class))
                    .orElseThrow(() -> new NoSuchElementException("Error al crear el producto"));
            ProductResponseDTO response = modelMapper.map(product, ProductResponseDTO.class);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * Actualiza un producto existente.
     * Solo accesible para usuarios con rol ADMIN.
     * 
     * @param id  Id del producto a actualizar.
     * @param dto Producto en formato {@code ProductRequestDTO}
     * @return Producto en formato {@code ProductResponseDTO}
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDTO> update(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO dto) {
        try {

            Product product = productService.update(id, modelMapper.map(dto, Product.class))
                    .orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
            ProductResponseDTO response = modelMapper.map(product, ProductResponseDTO.class);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Elimina un producto por su ID.
     * Solo accesible para usuarios con rol ADMIN.
     * 
     * @param id Id del producto a eliminar.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            productService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
