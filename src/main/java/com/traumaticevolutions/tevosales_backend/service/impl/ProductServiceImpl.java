package com.traumaticevolutions.tevosales_backend.service.impl;

import com.traumaticevolutions.tevosales_backend.dto.ProductRequestDTO;
import com.traumaticevolutions.tevosales_backend.dto.ProductResponseDTO;
import com.traumaticevolutions.tevosales_backend.model.Product;
import com.traumaticevolutions.tevosales_backend.repository.ProductRepository;
import com.traumaticevolutions.tevosales_backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de productos.
 * Utiliza {@code ProductRepository} y {@code ModelMapper} para convertir entre entidades y DTOs.
 * Gestiona la lógica de negocio para operaciones CRUD y búsqueda por nombre o categoría.
 * 
 * @author Ángel Aragón
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    /**
     * Obtiene la lista completa de productos.
     *
     * @return lista de productos en formato {@code ProductResponseDTO}
     */
    @Override
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Busca un producto por su ID.
     *
     * @param id identificador del producto
     * @return producto en formato {@code ProductResponseDTO}
     * @throws NoSuchElementException si no se encuentra el producto
     */
    @Override
    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
        return modelMapper.map(product, ProductResponseDTO.class);
    }

    /**
     * Crea un nuevo producto a partir de los datos recibidos.
     *
     * @param dto objeto con los datos del nuevo producto
     * @return producto guardado en formato {@code ProductResponseDTO}
     */
    @Override
    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = modelMapper.map(dto, Product.class);
        Product saved = productRepository.save(product);
        return modelMapper.map(saved, ProductResponseDTO.class);
    }

    /**
     * Actualiza un producto existente con los nuevos datos.
     *
     * @param id  identificador del producto a actualizar
     * @param dto objeto con los nuevos datos
     * @return producto actualizado en formato {@code ProductResponseDTO}
     * @throws NoSuchElementException si el producto no existe
     */
    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));

        modelMapper.map(dto, existing);
        Product updated = productRepository.save(existing);
        return modelMapper.map(updated, ProductResponseDTO.class);
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
     * Busca productos que contengan el texto indicado en el nombre (sin distinguir mayúsculas).
     *
     * @param name texto a buscar en el nombre
     * @return lista de productos coincidentes en formato {@code ProductResponseDTO}
     */
    @Override
    public List<ProductResponseDTO> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Busca productos por su categoría (sin distinguir mayúsculas).
     *
     * @param category categoría exacta a buscar
     * @return lista de productos de esa categoría en formato {@code ProductResponseDTO}
     */
    @Override
    public List<ProductResponseDTO> findByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category).stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());
    }
}
