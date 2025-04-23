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
 * 
 * @author Ángel Aragón
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));
        return modelMapper.map(product, ProductResponseDTO.class);
    }

    @Override
    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = modelMapper.map(dto, Product.class);
        Product saved = productRepository.save(product);
        return modelMapper.map(saved, ProductResponseDTO.class);
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));

        modelMapper.map(dto, existing); // sobreescribe los campos del existente
        Product updated = productRepository.save(existing);
        return modelMapper.map(updated, ProductResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Producto no encontrado");
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseDTO> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> findByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category).stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());
    }
}
