package com.traumaticevolutions.tevosales_backend.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.traumaticevolutions.tevosales_backend.dto.OrderRequestDTO;
import com.traumaticevolutions.tevosales_backend.model.Order;
import com.traumaticevolutions.tevosales_backend.model.enums.OrderStatus;

/**
 * Interfaz del servicio para gestionar pedidos.
 * Define operaciones CRUD y búsquedas específicas para pedidos del usuario
 * autenticado.
 * 
 * @author Ángel Aragón
 */
public interface OrderService {

    /**
     * Obtiene todos los pedidos del usuario autenticado de forma paginada.
     *
     * @param pageable información de paginación
     * @return página de pedidos del usuario
     */
    Page<Order> getAllOrdersAuthUserPaged(Pageable pageable);

    /**
     * Obtiene un pedido específico del usuario autenticado por su ID.
     *
     * @param id el identificador del pedido
     * @return el pedido encontrado, o null si no existe o no pertenece al usuario
     */
    Optional<Order> getOrderByIdAuthUser(Long id);

    /**
     * Busca un pedido por su ID.
     *
     * @param id el identificador del pedido
     * @return el pedido encontrado, o null si no existe
     */
    Optional<Order> findById(Long id);

    /**
     * Crea un nuevo pedido para el usuario autenticado.
     *
     * @param dto el pedido a crear
     * @return el pedido creado
     */
    Order create(OrderRequestDTO dto);

    /**
     * Elimina un pedido del usuario autenticado por su ID.
     *
     * @param id el identificador del pedido a eliminar
     * @return true si se eliminó correctamente, false si no existía o no pertenecía
     *         al usuario
     */
    boolean delete(Long id);

    /**
     * Actualiza el estado de un pedido por su ID.
     *
     * @param id     el identificador del pedido
     * @param status el nuevo estado del pedido
     * @return el pedido actualizado con el nuevo estado
     */
    Order updateStatus(Long id, OrderStatus status);

}