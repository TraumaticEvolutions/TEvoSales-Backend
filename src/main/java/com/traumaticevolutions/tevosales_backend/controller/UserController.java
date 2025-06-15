package com.traumaticevolutions.tevosales_backend.controller;

import com.traumaticevolutions.tevosales_backend.dto.UserRequestDTO;
import com.traumaticevolutions.tevosales_backend.dto.UserResponseAdminDTO;
import com.traumaticevolutions.tevosales_backend.dto.UserResponseDTO;
import com.traumaticevolutions.tevosales_backend.model.Role;
import com.traumaticevolutions.tevosales_backend.model.User;
import com.traumaticevolutions.tevosales_backend.service.RoleService;
import com.traumaticevolutions.tevosales_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

/**
 * Controlador REST para la gestión de usuarios.
 * Usa ModelMapper para mapear entre entidades y DTOs.
 * 
 * @author Ángel Aragón
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    /**
     * Endpoint para registrar un nuevo usuario.
     * Solo podrán acceder usuarios sin autenticación.
     * Registra al usuario con el rol "ROLE_CLIENTE".
     * Valida que el nombre de usuario, email y NIF no estén en uso.
     * 
     * @throws IllegalArgumentException si el nombre de usuario, email o NIF ya
     *                                  están en uso.
     *
     * @param userRequestDTO Datos del usuario a registrar.
     * @return Usuario registrado como DTO de respuesta.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDTO userRequestDTO) {
        Optional<Role> role = roleService.findByName("ROLE_CLIENTE");

        if (role.isEmpty()) {
            return ResponseEntity.badRequest().body("Rol CLIENTE no encontrado");
        }

        if (userService.findByUsername(userRequestDTO.getUsername()).isPresent()) {
            return ResponseEntity.status(409).body("El nombre de usuario ya está en uso");
        }
        if (userService.findByEmail(userRequestDTO.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("El email ya está en uso");
        }
        if (userService.findByNif(userRequestDTO.getNif()).isPresent()) {
            return ResponseEntity.status(409).body("El NIF ya está en uso");
        }
        User user = modelMapper.map(userRequestDTO, User.class);
        user.getRoles().add(role.get());

        try {
            User savedUser = userService.saveUser(user);
            UserResponseDTO responseDTO = modelMapper.map(savedUser, UserResponseDTO.class);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno al registrar el usuario");
        }
    }

    /**
     * Endpoint para obtener todos los usuarios con paginación y filtros.
     * Solo podrán acceder usuario con el rol admin usando
     * {@code @PreAuthorize("hasRole('ADMIN')")}.
     * 
     * @param page     Número de página, comienza en 0.
     * @param size     Tamaño de la página.
     * @param username Filtro por nombre de usuario.
     * @param email    Filtro por email.
     * @param nif      Filtro por NIF.
     * @param role     Filtro por rol (nombre del rol, ej: "ROLE_ADMIN").
     * @return Página de usuarios en formato DTO.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponseAdminDTO>> getAllUsersPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String nif,
            @RequestParam(required = false) String role) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        Page<User> users = userService.findUsersWithFilters(username, email, nif, role, pageable);
        Page<UserResponseAdminDTO> dtoPage = users.map(user -> modelMapper.map(user, UserResponseAdminDTO.class));
        return ResponseEntity.ok(dtoPage);
    }

    /**
     * Endpoint para actualizar los roles de un usuario.
     * Solo accesible por administradores.
     * 
     * @param userId ID del usuario a modificar.
     * @param roles  Lista de nombres de roles a asignar (ej: ["ROLE_ADMIN",
     *               "ROLE_CLIENTE"])
     * @return Usuario actualizado como DTO de respuesta.
     */
    @PutMapping("/roles/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUserRoles(
            @PathVariable Long userId,
            @RequestBody java.util.List<String> roles) {
        try {
            User updatedUser = userService.updateUserRoles(userId, roles);
            UserResponseDTO responseDTO = modelMapper.map(updatedUser, UserResponseDTO.class);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno al actualizar los roles del usuario");
        }
    }

    /**
     * Endpoint para eliminar un usuario por su ID.
     * Solo accesible por administradores.
     * 
     * @param userId ID del usuario a eliminar.
     * @return Mensaje de éxito o error.
     */
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        if (userService.deleteUser(userId)) {
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } else {
            return ResponseEntity.status(400).body("No se puede eliminar al usuario");
        }
    }
}
