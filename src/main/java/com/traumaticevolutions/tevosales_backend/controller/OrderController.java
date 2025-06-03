/**
 * package com.traumaticevolutions.tevosales_backend.controller;
 * 
 * import com.traumaticevolutions.tevosales_backend.dto.OrderRequestDTO;
 * import com.traumaticevolutions.tevosales_backend.dto.OrderResponseDTO;
 * import com.traumaticevolutions.tevosales_backend.service.OrderService;
 * import lombok.RequiredArgsConstructor;
 * import org.springframework.http.ResponseEntity;
 * import org.springframework.web.bind.annotation.*;
 * 
 * import java.util.List;
 * 
 * 
 * Controlador REST para gestionar los pedidos de los usuarios.
 * Permite crear nuevos pedidos y consultar los existentes del usuario
 * autenticado.
 * 
 * @author Ángel Aragón
 * 
 * @RestController
 *                 @RequestMapping("/api/orders")
 * @RequiredArgsConstructor
 *                          public class OrderController {
 * 
 *                          private final OrderService orderService;
 */
/**
 * Crea un nuevo pedido para el usuario autenticado.
 *
 * @param requestDTO datos del pedido
 * @return pedido creado
 * 
 * @PostMapping
 *              public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody
 *              OrderRequestDTO requestDTO) {
 *              OrderResponseDTO createdOrder = orderService.create(requestDTO);
 *              return ResponseEntity.ok(createdOrder);
 *              }
 */

/**
 * Obtiene todos los pedidos del usuario autenticado.
 *
 * @return lista de pedidos
 * 
 * @GetMapping
 *             public ResponseEntity<List<OrderResponseDTO>> getUserOrders() {
 *             List<OrderResponseDTO> orders =
 *             orderService.findByAuthenticatedUser();
 *             return ResponseEntity.ok(orders);
 *             }
 */

/**
 * Obtiene un pedido específico por ID, solo si pertenece al usuario
 * autenticado.
 *
 * @param id identificador del pedido
 * @return pedido si existe y pertenece al usuario
 * 
 *         @GetMapping("/{id}")
 *         public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable
 *         Long id) {
 *         OrderResponseDTO order =
 *         orderService.findByIdAndAuthenticatedUser(id);
 *         return ResponseEntity.ok(order);
 *         }
 * 
 *         }
 */
