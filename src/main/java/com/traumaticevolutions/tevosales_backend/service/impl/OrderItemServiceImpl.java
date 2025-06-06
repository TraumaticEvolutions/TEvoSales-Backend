package com.traumaticevolutions.tevosales_backend.service.impl;

import com.traumaticevolutions.tevosales_backend.model.Order;
import com.traumaticevolutions.tevosales_backend.model.OrderItem;
import com.traumaticevolutions.tevosales_backend.model.User;
import com.traumaticevolutions.tevosales_backend.repository.OrderItemRepository;
import com.traumaticevolutions.tevosales_backend.repository.OrderRepository;
import com.traumaticevolutions.tevosales_backend.repository.UserRepository;
import com.traumaticevolutions.tevosales_backend.service.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

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
    private final UserRepository userRepository;

    /**
     * Crea un nuevo ítem de pedido.
     *
     * @param orderItem el ítem a guardar
     * @return el ítem guardado
     */
    @Override
    public OrderItem create(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    /**
     * Guarda una lista de ítems de pedido.
     *
     * @param orderItems lista de ítems
     * @return ítems guardados
     */
    @Override
    public List<OrderItem> saveAll(List<OrderItem> orderItems) {
        return orderItemRepository.saveAll(orderItems);
    }

    /**
     * Recupera los ítems de un pedido del usuario autenticado.
     *
     * @param orderId identificador del pedido
     * @return lista de ítems
     */
    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new EntityNotFoundException("No tienes permiso para acceder a este pedido");
        }

        return orderItemRepository.findByOrder(order);
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

    /**
     * Elimina todos los ítems asociados a un pedido.
     *
     * @param orderId el ID del pedido cuyos ítems se eliminarán
     */
    @Override
    public void deleteByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + orderId));
        orderItemRepository.deleteByOrder(order);
    }

}