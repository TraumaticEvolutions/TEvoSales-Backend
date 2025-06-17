package com.traumaticevolutions.tevosales_backend.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.traumaticevolutions.tevosales_backend.dto.OrderItemRequestDTO;
import com.traumaticevolutions.tevosales_backend.dto.OrderRequestDTO;
import com.traumaticevolutions.tevosales_backend.model.Order;
import com.traumaticevolutions.tevosales_backend.model.OrderItem;
import com.traumaticevolutions.tevosales_backend.model.Product;
import com.traumaticevolutions.tevosales_backend.model.User;
import com.traumaticevolutions.tevosales_backend.model.enums.OrderStatus;
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
     * Valida el stock de los productos y actualiza sus cantidades.
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
        order.setPostalCode(dto.getPostalCode());
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemDTO : dto.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new NoSuchElementException(
                            "Producto no encontrado con ID: " + itemDTO.getProductId()));
            int newStock = product.getStock() - itemDTO.getQuantity();
            if (newStock < 0) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + product.getName());
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setOrder(order);

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
            orderItem.setSubtotal(subtotal);

            total = total.add(subtotal);
            orderItems.add(orderItem);
            product.setStock(newStock);
            productRepository.save(product);
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
     * Actualiza el estado de un pedido por su ID.
     *
     * @param id     el identificador del pedido
     * @param status el nuevo estado del pedido
     * @return el pedido actualizado con el nuevo estado
     */
    @Override
    public Order updateStatus(Long id, OrderStatus status) {
        Order existing = orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pedido no encontrado"));
        existing.setStatus(status);
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

    /**
     * Obtiene todos los pedidos del usuario autenticado de forma paginada.
     *
     * @param pageable información de paginación
     * @param start    fecha de inicio del rango de búsqueda (opcional)
     * @param end      fecha de fin del rango de búsqueda (opcional)
     * @return página de pedidos del usuario
     */
    @Override
    public Page<Order> getAllOrdersAuthUserPaged(Pageable pageable, LocalDateTime start, LocalDateTime end) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado"));

        if (start != null && end != null) {
            return orderRepository.findByUserAndCreatedAtBetween(user, start, end, pageable);
        } else if (start != null) {
            return orderRepository.findByUserAndCreatedAtAfter(user, start, pageable);
        } else if (end != null) {
            return orderRepository.findByUserAndCreatedAtBefore(user, end, pageable);
        } else {
            return orderRepository.findByUser(user, pageable);
        }
    }

    /**
     * Obtiene todos los pedidos con filtros aplicados.
     *
     * @param pageable  información de paginación
     * @param username  filtro por nombre de usuario (opcional)
     * @param status    filtro por estado del pedido (opcional)
     * @param startDate fecha de inicio del rango de búsqueda (opcional)
     * @param endDate   fecha de fin del rango de búsqueda (opcional)
     * @return página de pedidos que coinciden con los filtros
     */
    @Override
    public Page<Order> getAllOrdersPaged(Pageable pageable, String username, String status, LocalDateTime startDate,
            LocalDateTime endDate) {
        Specification<Order> spec = (root, query, cb) -> cb.conjunction();

        if (username != null && !username.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("user").get("username")),
                    "%" + username.toLowerCase() + "%"));
        }
        if (status != null && !status.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), OrderStatus.valueOf(status)));
        }
        if (startDate != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("createdAt"), startDate));
        }
        if (endDate != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("createdAt"), endDate));
        }

        return orderRepository.findAll(spec, pageable);
    }

}