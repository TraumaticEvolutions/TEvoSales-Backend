package com.traumaticevolutions.tevosales_backend.controller;

import com.traumaticevolutions.tevosales_backend.dto.ProductRequestDTO;
import com.traumaticevolutions.tevosales_backend.dto.ProductResponseDTO;
import com.traumaticevolutions.tevosales_backend.dto.ProductSalesDTO;
import com.traumaticevolutions.tevosales_backend.model.Product;
import com.traumaticevolutions.tevosales_backend.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
     * Devuelve todos los productos paginados.
     * 
     * @param name     Nombre del producto a filtrar (opcional)
     * @param brand    Marca del producto a filtrar (opcional)
     * @param category Categoría del producto a filtrar (opcional)
     * @param page     Número de página (0 por defecto)
     * @param size     Tamaño de página (10 por defecto)
     * @param sort     Criterios de ordenación (formato: campo,dirección)
     *                 Dirección puede ser 'asc' o 'desc' (por defecto 'id,asc')
     * @return Página de productos en formato {@code ProductResponseDTO}
     * @author Ángel Aragón
     */
    @GetMapping
    public ResponseEntity<?> getAllPaged(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(defaultValue = "id,asc") String sort) {
        try {
            Page<Product> products = productService.findAllPaged(name, brand, category, page, size, sort);
            Page<ProductResponseDTO> dtoPage = products
                    .map(product -> modelMapper.map(product, ProductResponseDTO.class));
            return ResponseEntity.ok(dtoPage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error en los parámetros de ordenación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno al obtener productos: " + e.getMessage());
        }
    }

    /**
     * Devuelve productos aleatorios (4).
     * 
     * @return Lista de productos aleatorios en formato {@code ProductResponseDTO}
     */
    @GetMapping("/random")
    public ResponseEntity<List<ProductResponseDTO>> getRandomProducts() {
        List<Product> allProducts = productService.findAll();
        Collections.shuffle(allProducts);
        List<Product> randomProducts = allProducts.stream().limit(4).toList();
        List<ProductResponseDTO> response = randomProducts.stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .toList();
        return ResponseEntity.ok(response);
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
     * @param dto Producto en formato {@code ProductRequestDTO}
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
     * @return Respuesta HTTP sin contenido (204) si se eliminó correctamente,
     *         o 404 si no se encontró el producto, o 400 si hubo un error.
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

    /**
     * Devuelve los 5 productos más vendidos y su cantidad.
     * 
     * @return lista de productos y cantidades vendidas
     */
    @GetMapping("/top-sellers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductSalesDTO>> getTop5BestSellers() {
        return ResponseEntity.ok(productService.getTop5BestSellers());
    }
}
