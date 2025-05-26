package com.traumaticevolutions.tevosales_backend.service.impl;

import com.traumaticevolutions.tevosales_backend.model.Order;
import com.traumaticevolutions.tevosales_backend.model.OrderItem;
import com.traumaticevolutions.tevosales_backend.repository.OrderItemRepository;
import com.traumaticevolutions.tevosales_backend.service.OrderItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio para la gestión de ítems de pedidos.
 * Encapsula operaciones CRUD y consultas relacionadas con los ítems.
 * 
 * @author Ángel Aragón
 */
@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    /**
     * Crea y guarda un nuevo ítem de pedido.
     *
     * @param orderItem el ítem a crear
     * @return el ítem creado y persistido
     */
    @Override
    @Transactional
    public OrderItem create(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    /**
     * Guarda una lista de ítems de pedido en la base de datos.
     *
     * @param orderItems ítems a guardar
     * @return lista de ítems guardados
     */
    @Override
    @Transactional
    public List<OrderItem> saveAll(List<OrderItem> orderItems) {
        return orderItemRepository.saveAll(orderItems);
    }

    /**
     * Obtiene todos los ítems asociados a un pedido.
     *
     * @param order el pedido del cual se buscan los ítems
     * @return lista de ítems pertenecientes al pedido
     */
    @Override
    public List<OrderItem> findByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }

}
