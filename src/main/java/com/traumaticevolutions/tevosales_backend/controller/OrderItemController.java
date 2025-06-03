/**
 * package com.traumaticevolutions.tevosales_backend.controller;
 * 
 * import com.traumaticevolutions.tevosales_backend.dto.OrderItemResponseDTO;
 * import com.traumaticevolutions.tevosales_backend.dto.OrderResponseDTO;
 * import com.traumaticevolutions.tevosales_backend.model.Order;
 * import com.traumaticevolutions.tevosales_backend.model.OrderItem;
 * import com.traumaticevolutions.tevosales_backend.service.OrderItemService;
 * import com.traumaticevolutions.tevosales_backend.service.OrderService;
 * import lombok.RequiredArgsConstructor;
 * 
 * import org.apache.catalina.mapper.Mapper;
 * import org.springframework.http.ResponseEntity;
 * import org.springframework.web.bind.annotation.*;
 * 
 * import java.util.List;
 * 
 * 
 * Controlador REST para gestionar los ítems de pedidos.
 * Ofrece un único endpoint para obtener los productos de un pedido específico.
 *
 * @author Ángel Aragón
 * 
 * @RestController
 *                 @RequestMapping("/api/order-items")
 * @RequiredArgsConstructor
 *                          public class OrderItemController {
 */
/**
 * private final OrderItemService orderItemService;
 * private final OrderService orderService;
 * 
 * 
 * Obtiene los ítems de un pedido específico.
 *
 * @param orderId el ID del pedido
 * @return lista de ítems como DTO
 * 
 *         @GetMapping("/order/{orderId}")
 *         public ResponseEntity<List<OrderItemResponseDTO>>
 *         getItemsByOrder(@PathVariable Long orderId) {
 *         Order order = orderService.findByIdAndAuthenticatedUser(orderId);
 *         List<OrderItem> orderItems = orderItemService.findByOrder(order);
 *         List<OrderItemResponseDTO> responseDTOs = orderItems.stream()
 *         .map(item -> modelMapper.map(item, OrderItemResponseDTO.class))
 *         .toList();
 * 
 *         return ResponseEntity.ok(responseDTOs);
 *         }
 *         }
 *         }
 */
