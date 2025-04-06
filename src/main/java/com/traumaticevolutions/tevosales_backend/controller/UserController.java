package com.traumaticevolutions.tevosales_backend.controller;

import com.traumaticevolutions.tevosales_backend.dto.UserRequestDTO;
import com.traumaticevolutions.tevosales_backend.dto.UserResponseDTO;
import com.traumaticevolutions.tevosales_backend.model.Role;
import com.traumaticevolutions.tevosales_backend.model.User;
import com.traumaticevolutions.tevosales_backend.service.RoleService;
import com.traumaticevolutions.tevosales_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     *
     * @param userRequestDTO Datos del usuario a registrar.
     * @return Usuario registrado como DTO de respuesta.
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequestDTO) {
        Optional<Role> role = roleService.findByName("CLIENTE");

        if (role.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        User user = modelMapper.map(userRequestDTO, User.class);
        user.getRoles().add(role.get());

        User savedUser = userService.saveUser(user);
        UserResponseDTO responseDTO = modelMapper.map(savedUser, UserResponseDTO.class);

        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Endpoint para obtener todos los usuarios.
     * Solo podrán acceder usuario con el rol admin usando {@code @PreAuthorize("hasRole('ADMIN')")}.
     * @return Lista de usuarios en formato DTO.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        List<UserResponseDTO> responseList = users.stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    /**
     * Endpoint para obtener un usuario por su email.
     *
     * @param email Email del usuario.
     * @return Usuario encontrado como DTO.
     */
    @GetMapping("/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);

        return user.map(value -> {
            UserResponseDTO dto = modelMapper.map(value, UserResponseDTO.class);
            return ResponseEntity.ok(dto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
