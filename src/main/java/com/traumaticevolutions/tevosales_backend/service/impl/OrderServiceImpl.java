/*
 * package com.traumaticevolutions.tevosales_backend.service.impl;
 * 
 * import com.traumaticevolutions.tevosales_backend.dto.OrderItemRequestDTO;
 * import com.traumaticevolutions.tevosales_backend.dto.OrderRequestDTO;
 * import com.traumaticevolutions.tevosales_backend.dto.OrderResponseDTO;
 * import com.traumaticevolutions.tevosales_backend.model.Order;
 * import com.traumaticevolutions.tevosales_backend.model.OrderItem;
 * import com.traumaticevolutions.tevosales_backend.model.Product;
 * import com.traumaticevolutions.tevosales_backend.model.User;
 * import com.traumaticevolutions.tevosales_backend.repository.OrderRepository;
 * import
 * com.traumaticevolutions.tevosales_backend.repository.ProductRepository;
 * import com.traumaticevolutions.tevosales_backend.service.OrderService;
 * import jakarta.transaction.Transactional;
 * import lombok.RequiredArgsConstructor;
 * import org.modelmapper.ModelMapper;
 * import org.springframework.security.core.context.SecurityContextHolder;
 * import org.springframework.stereotype.Service;
 * 
 * import java.math.BigDecimal;
 * import java.util.HashSet;
 * import java.util.List;
 * import java.util.stream.Collectors;
 * 
 * /**
 * Implementación de la interfaz {@link OrderService}.
 * Gestiona la lógica de negocio relacionada con los pedidos de los usuarios.
 * 
 * @author Ángel Aragón
 * 
 * @Service
 * 
 * @RequiredArgsConstructor
 * public class OrderServiceImpl implements OrderService {
 * 
 * private final ProductRepository productRepository;
 * private final OrderRepository orderRepository;
 * private final ModelMapper modelMapper;
 */
/**
 * Crea un nuevo pedido para el usuario autenticado.
 * Valida que el usuario esté autenticado y que los productos existan.
 * 
 * @param dto datos del pedido a crear
 * @return el pedido creado como DTO
 * @throws RuntimeException si el usuario no está autenticado o si algún
 *                          producto no se encuentra
 * 
 * @Override
 * @Transactional
 *                public OrderResponseDTO create(OrderRequestDTO dto) {
 *                User user = getAuthenticatedUser();
 *                Order order = new Order();
 *                order.setUser(user);
 *                order.setAddress(dto.getAddress());
 *                order.setNumber(dto.getNumber());
 *                order.setFloor(dto.getFloor());
 *                order.setStatus(com.traumaticevolutions.tevosales_backend.model.enums.OrderStatus.PENDING);
 * 
 *                BigDecimal total = BigDecimal.ZERO;
 *                HashSet<OrderItem> items = new HashSet<>();
 * 
 *                for (OrderItemRequestDTO itemDTO : dto.getItems()) {
 *                Product product =
 *                productRepository.findById(itemDTO.getProductId())
 *                .orElseThrow(() -> new RuntimeException("Producto no
 *                encontrado"));
 * 
 *                OrderItem orderItem = new OrderItem();
 *                orderItem.setOrder(order);
 *                orderItem.setProduct(product);
 *                orderItem.setQuantity(itemDTO.getQuantity());
 *                orderItem.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));
 * 
 *                items.add(orderItem);
 *                total = total.add(orderItem.getSubtotal());
 *                }
 * 
 *                order.setOrderItems(items);
 *                order.setTotal(total);
 * 
 *                Order savedOrder = orderRepository.save(order);
 *                return modelMapper.map(savedOrder, OrderResponseDTO.class);
 *                }
 */

/**
 * Encuentra todos los pedidos del usuario autenticado.
 * 
 * @return lista de pedidos del usuario como DTOs
 * @return lista de pedidos del usuario autenticado
 * @throws RuntimeException si el usuario no está autenticado
 * 
 * @Override
 *           public List<OrderResponseDTO> findByAuthenticatedUser() {
 *           User user = getAuthenticatedUser();
 *           return orderRepository.findByUser(user).stream()
 *           .map(order -> modelMapper.map(order, OrderResponseDTO.class))
 *           .collect(Collectors.toList());
 * 
 *           }
 */

/**
 * Borra un pedido por su ID.
 * Solo puede eliminar pedidos del usuario autenticado.
 * 
 * @param id identificador del pedido a eliminar
 * @throws RuntimeException si el usuario no está autenticado
 * 
 * @Override
 *           public boolean delete(Long id) {
 *           orderRepository.deleteById(id);
 *           return !orderRepository.existsById(id);
 *           }
 */

/**
 * Obtiene el usuario autenticado desde el contexto de seguridad.
 * 
 * @return el usuario autenticado
 * @throws RuntimeException si no hay un usuario autenticado
 * 
 *                          private User getAuthenticatedUser() {
 *                          Object principal =
 *                          SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 *                          if (!(principal instanceof User)) {
 *                          throw new RuntimeException("Usuario no
 *                          autenticado");
 *                          }
 *                          return (User) principal;
 *                          }
 */
/*
 * @Override
 * public OrderResponseDTO findById(Long id) {
 * 
 * }
 * 
 * 
 * @Override
 * public OrderResponseDTO findByIdAndUser(Long id) {
 * // TODO Auto-generated method stub
 * throw new
 * UnsupportedOperationException("Unimplemented method 'findByIdAndUser'");
 * }
 * 
 * @Override
 * public OrderResponseDTO update(Long id, OrderRequestDTO orderRequestDTO) {
 * // TODO Auto-generated method stub
 * throw new UnsupportedOperationException("Unimplemented method 'update'");
 * }
 * 
 * @Override
 * public boolean deleteByIdAndUser(Long id, User user) {
 * // TODO Auto-generated method stub
 * throw new
 * UnsupportedOperationException("Unimplemented method 'deleteByIdAndUser'");
 * }
 * 
 * @Override
 * public OrderResponseDTO findById(Long id) {
 * // TODO Auto-generated method stub
 * throw new UnsupportedOperationException("Unimplemented method 'findById'");
 * }
 * 
 * }
 */
