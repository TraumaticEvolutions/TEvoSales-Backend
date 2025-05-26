package com.traumaticevolutions.tevosales_backend.service.impl;

import com.traumaticevolutions.tevosales_backend.dto.OrderRequestDTO;
import com.traumaticevolutions.tevosales_backend.dto.OrderResponseDTO;
import com.traumaticevolutions.tevosales_backend.model.Order;
import com.traumaticevolutions.tevosales_backend.model.OrderItem;
import com.traumaticevolutions.tevosales_backend.model.Product;
import com.traumaticevolutions.tevosales_backend.model.User;
import com.traumaticevolutions.tevosales_backend.repository.OrderRepository;
import com.traumaticevolutions.tevosales_backend.repository.ProductRepository;
import com.traumaticevolutions.tevosales_backend.repository.UserRepository;
import com.traumaticevolutions.tevosales_backend.service.OrderItemService;
import com.traumaticevolutions.tevosales_backend.service.OrderService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para la entidad {@link Order}.
 * Gestiona la creación, obtención y eliminación de pedidos,
 * así como el cálculo del precio total de cada pedido.
 * 
 * @author Ángel Aragón
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderItemService orderItemService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /**
     * Crea un nuevo pedido a partir de un DTO de entrada.
     * Calcula los subtotales por producto y el total del pedido.
     *
     * @param orderRequestDTO Datos del pedido proporcionados por el cliente
     * @return DTO con la información del pedido creado
     */
    @Override
    @Transactional
    public OrderResponseDTO create(OrderRequestDTO orderRequestDTO) {
        Order order = modelMapper.map(orderRequestDTO, Order.class);
        order.setUser(getAuthenticatedUser());

        // Guardamos el pedido para tener su ID
        order = orderRepository.save(order);

        BigDecimal total = BigDecimal.ZERO;

        for (var itemDto : orderRequestDTO.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            total = total.add(subtotal);

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setSubtotal(subtotal);

            orderItemService.create(item);
        }

        order.setTotal(total);
        return modelMapper.map(orderRepository.save(order), OrderResponseDTO.class);
    }

    /**
     * Obtiene todos los pedidos del usuario autenticado.
     *
     * @return Lista de DTOs con los pedidos del usuario
     */
    @Override
    public List<OrderResponseDTO> findByAuthenticatedUser() {
        User user = getAuthenticatedUser();
        List<Order> orders = orderRepository.findByUser(user);
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Elimina un pedido por su ID.
     *
     * @param id Identificador del pedido
     */
    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    /**
     * Obtiene el usuario autenticado a partir del contexto de seguridad.
     *
     * @return el usuario autenticado
     */
    private User getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername()
                : principal.toString();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
