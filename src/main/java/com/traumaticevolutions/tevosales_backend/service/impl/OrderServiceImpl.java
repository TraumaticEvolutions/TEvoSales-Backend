package com.traumaticevolutions.tevosales_backend.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.traumaticevolutions.tevosales_backend.dto.OrderItemRequestDTO;
import com.traumaticevolutions.tevosales_backend.dto.OrderRequestDTO;
import com.traumaticevolutions.tevosales_backend.model.Order;
import com.traumaticevolutions.tevosales_backend.model.OrderItem;
import com.traumaticevolutions.tevosales_backend.model.Product;
import com.traumaticevolutions.tevosales_backend.model.User;
import com.traumaticevolutions.tevosales_backend.repository.OrderRepository;
import com.traumaticevolutions.tevosales_backend.repository.ProductRepository;
import com.traumaticevolutions.tevosales_backend.repository.UserRepository;
import com.traumaticevolutions.tevosales_backend.service.OrderService;

import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de pedidos.
 * Gestiona la lógica de negocio para operaciones CRUD de pedidos.
 * 
 * 
 * @author Ángel Aragón
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    /**
     * Obtiene todos los pedidos del usuario autenticado.
     *
     * @return lista de pedidos del usuario
     */
    @Override
    public List<Order> getAllOrdersAuthUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));
        return orderRepository.findByUser(user);
    }

    /**
     * Obtiene un pedido específico del usuario autenticado por su ID.
     *
     * @param id el identificador del pedido
     * @return el pedido encontrado, o null si no existe o no pertenece al usuario
     */
    @Override
    public Optional<Order> getOrderByIdAuthUser(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pedido no encontrado"));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        if (!order.getUser().getId().equals(user.getId())) {
            throw new NoSuchElementException("No tienes permiso para acceder a este pedido");
        }
        return Optional.of(order);
    }

    /**
     * Crea un nuevo pedido para el usuario autenticado.
     * 
     * @param dto el pedido a crear
     * @return el pedido creado
     */
    @Override
    public Order create(OrderRequestDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        Order order = new Order();
        order.setUser(user);
        order.setAddress(dto.getAddress());
        order.setNumber(dto.getNumber());
        order.setFloor(dto.getFloor());

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemDTO : dto.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new NoSuchElementException(
                            "Producto no encontrado con ID: " + itemDTO.getProductId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setOrder(order);

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
            orderItem.setSubtotal(subtotal);

            total = total.add(subtotal);
            orderItems.add(orderItem);
        }

        order.setOrderItems(new java.util.HashSet<>(orderItems));
        order.setTotal(total);

        return orderRepository.save(order);
    }

    /**
     * Elimina un pedido del usuario autenticado por su ID.
     *
     * @param id el identificador del pedido a eliminar
     * @return true si se eliminó correctamente, false si no existía o no pertenecía
     *         al usuario
     */
    @Override
    public boolean delete(Long id) {
        orderRepository.deleteById(id);
        return true;
    }

    /**
     * Actualiza un pedido existente del usuario autenticado.
     *
     * @param id    el identificador del pedido a actualizar
     * @param order los nuevos datos del pedido
     * @return el pedido actualizado, o null si no existe o no pertenece al usuario
     */
    @Override
    public Order update(Long id, Order newO) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pedido no encontrado"));

        if (newO.getStatus() != null) {
            existing.setStatus(newO.getStatus());
        }

        if (newO.getAddress() != null && !newO.getAddress().trim().isEmpty()) {
            existing.setAddress(newO.getAddress());
        }

        if (newO.getNumber() != null && !newO.getNumber().trim().isEmpty()) {
            existing.setNumber(newO.getNumber());
        }

        if (newO.getFloor() != null && !newO.getFloor().trim().isEmpty()) {
            existing.setFloor(newO.getFloor());
        }

        if (newO.getOrderItems() != null && !newO.getOrderItems().isEmpty()) {
            existing.setOrderItems(newO.getOrderItems());
        }

        if (newO.getTotal() != null) {
            existing.setTotal(newO.getTotal());
        }

        if (newO.getUser() != null) {
            existing.setUser(newO.getUser());
        }

        return orderRepository.save(existing);
    }

    /**
     * Busca un pedido por su ID.
     *
     * @param id el identificador del pedido
     * @return el pedido encontrado, o null si no existe
     */
    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
}