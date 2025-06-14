package com.traumaticevolutions.tevosales_backend.controller;

import com.traumaticevolutions.tevosales_backend.dto.OrderRequestDTO;
import com.traumaticevolutions.tevosales_backend.dto.OrderResponseDTO;
import com.traumaticevolutions.tevosales_backend.model.Order;
import com.traumaticevolutions.tevosales_backend.model.enums.OrderStatus;
import com.traumaticevolutions.tevosales_backend.service.OrderService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
     * Obtiene todos los pedidos del usuario autenticado de forma paginada.
     *
     * @param page número de página a obtener
     * @param size tamaño de la página
     * @return página de pedidos en formato {@code Page<OrderResponseDTO>}
     */
    @GetMapping("/paged")
    public ResponseEntity<Page<OrderResponseDTO>> getUserOrdersPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderService.getAllOrdersAuthUserPaged(pageable);
        Page<OrderResponseDTO> dtoPage = orders.map(order -> modelMapper.map(order, OrderResponseDTO.class));
        return ResponseEntity.ok(dtoPage);
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
     * Actualiza el estado de un pedido por su ID.
     * 
     * @param id     ID del pedido a actualizar
     * @param status Nuevo estado del pedido
     * @return Pedido actualizado en formato {@code OrderResponseDTO}
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(@PathVariable Long id,
            @RequestBody String status) {
        OrderStatus orderStatus = OrderStatus.valueOf(status);
        OrderResponseDTO updatedOrder = modelMapper.map(orderService.updateStatus(id, orderStatus),
                OrderResponseDTO.class);
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