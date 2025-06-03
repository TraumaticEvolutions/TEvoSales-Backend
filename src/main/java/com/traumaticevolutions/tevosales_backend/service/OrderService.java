package com.traumaticevolutions.tevosales_backend.service;

import com.traumaticevolutions.tevosales_backend.dto.OrderRequestDTO;
import com.traumaticevolutions.tevosales_backend.dto.OrderResponseDTO;
import com.traumaticevolutions.tevosales_backend.model.User;

import java.util.List;

/**
 * Servicio que define las operaciones disponibles para gestionar pedidos.
 * 
 * @author Ángel Aragón
 */
public interface OrderService {

    /**
     * Crea un nuevo pedido para el usuario autenticado.
     *
     * @param orderRequestDTO datos del pedido
     * @return pedido creado
     */
    OrderResponseDTO create(OrderRequestDTO orderRequestDTO);

    /**
     * Obtiene todos los pedidos del usuario autenticado.
     *
     * @return lista de pedidos
     */
    List<OrderResponseDTO> findByAuthenticatedUser();

    /**
     * Elimina un pedido por su ID si pertenece al usuario autenticado.
     *
     * @param orderId identificador del pedido
     */
    boolean delete(Long orderId);

    /**
     * Busca un pedido por su ID, verificando que pertenezca al usuario autenticado.
     *
     * @param id identificador del pedido
     * @return DTO del pedido si pertenece al usuario
     */
    OrderResponseDTO findById(Long id);

    /**
     * Busca un pedido por su ID, verificando que pertenezca al usuario autenticado.
     *
     * @param id identificador del pedido
     * @return DTO del pedido si pertenece al usuario
     */
    OrderResponseDTO findByIdAndUser(Long id);

    /**
     * Actualiza un pedido existente.
     *
     * @param id              identificador del pedido a actualizar
     * @param orderRequestDTO nuevos datos del pedido
     * @return DTO del pedido actualizado
     */
    OrderResponseDTO update(Long id, OrderRequestDTO orderRequestDTO);

    /**
     * Elimina un pedido por su ID y el usuario asociado.
     *
     * @param id   identificador del pedido
     * @param user usuario autenticado
     * @return true si se eliminó correctamente, false si no existía o no pertenecía
     *         al usuario
     */
    boolean deleteByIdAndUser(Long id, User user);
}
