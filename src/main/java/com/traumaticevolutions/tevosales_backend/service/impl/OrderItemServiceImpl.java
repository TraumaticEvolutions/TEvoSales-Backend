package com.traumaticevolutions.tevosales_backend.service.impl;

import com.traumaticevolutions.tevosales_backend.config.MapperConfig;
import com.traumaticevolutions.tevosales_backend.dto.OrderItemRequestDTO;
import com.traumaticevolutions.tevosales_backend.dto.OrderItemResponseDTO;
import com.traumaticevolutions.tevosales_backend.model.Order;
import com.traumaticevolutions.tevosales_backend.model.OrderItem;
import com.traumaticevolutions.tevosales_backend.model.User;
import com.traumaticevolutions.tevosales_backend.repository.OrderItemRepository;
import com.traumaticevolutions.tevosales_backend.repository.OrderRepository;
import com.traumaticevolutions.tevosales_backend.service.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio {@link OrderItemService}.
 * Gestiona operaciones sobre los ítems de pedidos.
 * 
 * @author Ángel Aragón
 */
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    /**
     * Guarda un ítem de pedido.
     *
     * @param orderItemRequestDTO el ítem a guardar
     * @return el ítem guardado
     */
    @Override
    public OrderItemResponseDTO create(OrderItemRequestDTO dto) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        OrderItem orderItem = modelMapper.map(dto, OrderItem.class);
        orderItemRepository.save(orderItem);
        return modelMapper.map(orderItemRepository, OrderItemResponseDTO.class);
    }

    /**
     * Guarda una lista de ítems de pedido.
     *
     * @param orderItems lista de ítems
     * @return ítems guardados
     */
    @Override
    public List<OrderItemResponseDTO> saveAll(List<OrderItemRequestDTO> orderItems) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        List<OrderItem> orderItemsToSave = orderItems.stream()
                .map(dto -> modelMapper.map(dto, OrderItem.class))
                .collect(Collectors.toList());
        orderItemsToSave.forEach(item -> {
            BigDecimal subtotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            item.setSubtotal(subtotal);
        });
        orderItemRepository.saveAll(orderItemsToSave);
        return orderItemsToSave.stream()
                .map(item -> modelMapper.map(item, OrderItemResponseDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Recupera los ítems de un pedido del usuario autenticado.
     *
     * @param orderId identificador del pedido
     * @return lista de DTOs de ítems
     */
    @Override
    public List<OrderItemResponseDTO> findByOrderId(Long orderId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new SecurityException("Este pedido no te pertenece.");
        }

        List<OrderItem> items = orderItemRepository.findByOrder(order);

        return items.stream()
                .map(item -> modelMapper.map(item, OrderItemResponseDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Calcula y construye un {@link OrderItem} a partir de un pedido, producto y
     * cantidad.
     * El subtotal se calcula automáticamente como precio * cantidad.
     *
     * @param order    el pedido al que pertenece
     * @param product  el producto asociado
     * @param quantity la cantidad solicitada
     * @return el ítem creado con subtotal
     */
    public OrderItem buildOrderItemFrom(Order order, com.traumaticevolutions.tevosales_backend.model.Product product,
            int quantity) {
        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(quantity);

        BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        item.setSubtotal(subtotal);

        return item;
    }

}