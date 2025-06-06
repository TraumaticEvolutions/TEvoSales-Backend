package com.traumaticevolutions.tevosales_backend.controller;

import com.traumaticevolutions.tevosales_backend.dto.OrderRequestDTO;
import com.traumaticevolutions.tevosales_backend.dto.OrderResponseDTO;
import com.traumaticevolutions.tevosales_backend.model.Order;
import com.traumaticevolutions.tevosales_backend.service.OrderService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
/**
 * Controlador REST para la gestión de pedidos.
 * Expone endpoints para crear y consultar pedidos del usuario autenticado.
 * 
 * @author Ángel Aragón
 */
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    /**
     * Crea un nuevo pedido para el usuario autenticado.
     * 
     * @param requestDTO Datos del pedido a crear
     * @return Pedido creado en formato {@code OrderResponseDTO}
     */
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO requestDTO) {
        Order createdOrder = orderService.create(requestDTO);
        OrderResponseDTO responseDTO = modelMapper.map(createdOrder, OrderResponseDTO.class);
        return ResponseEntity.status(201).body(responseDTO);
    }

    /**
     * Obtiene todos los pedidos del usuario autenticado.
     * 
     * @return Lista de pedidos en formato {@code List<OrderResponseDTO>}
     */
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getUserOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrdersAuthUser().stream()
                .map(order -> modelMapper.map(order, OrderResponseDTO.class))
                .toList();
        return ResponseEntity.ok(orders);
    }

    /**
     * Obtiene un pedido específico del usuario autenticado por su ID.
     * 
     * @param id ID del pedido a consultar
     * @return Pedido encontrado en formato {@code OrderResponseDTO}, o 404 si no
     *         existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        OrderResponseDTO order = modelMapper.map(orderService.getOrderByIdAuthUser(id), OrderResponseDTO.class);
        return ResponseEntity.ok(order);
    }

    /**
     * Actualiza un pedido del usuario autenticado.
     * 
     * @param id         ID del pedido a actualizar
     * @param requestDTO Nuevos datos del pedido
     * @return Pedido actualizado en formato {@code OrderResponseDTO}
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable Long id,
            @RequestBody OrderRequestDTO requestDTO) {
        Order order = modelMapper.map(requestDTO, Order.class);
        OrderResponseDTO updatedOrder = modelMapper.map(orderService.update(id, order), OrderResponseDTO.class);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * Elimina un pedido del usuario autenticado.
     * 
     * @param id ID del pedido a eliminar
     * @return Respuesta HTTP indicando el resultado de la operación
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            if (orderService.delete(id)) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}