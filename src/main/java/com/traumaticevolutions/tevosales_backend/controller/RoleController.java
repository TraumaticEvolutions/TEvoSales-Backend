package com.traumaticevolutions.tevosales_backend.controller;

import com.traumaticevolutions.tevosales_backend.dto.RoleResponseDTO;
import com.traumaticevolutions.tevosales_backend.model.Role;
import com.traumaticevolutions.tevosales_backend.service.RoleService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para la gestión de roles.
 * Expone endpoints para crear, actualizar, eliminar y consultar roles del
 * sistema.
 * 
 * @author Ángel Aragón
 */
@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final ModelMapper modelMapper;

    /**
     * Endpoint para obtener todos los roles del sistema.
     * Solo accesible por usuarios con rol ADMIN.
     *
     * @return Lista de roles como DTOs.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        List<Role> roles = roleService.findAll();
        List<RoleResponseDTO> dtos = roles.stream()
                .map(role -> modelMapper.map(role, RoleResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Endpoint para obtener todos los roles del sistema paginados y filtrados.
     * Solo accesible por usuarios con rol ADMIN.
     *
     * @param page Número de página a obtener (0-indexed).
     * @param size Tamaño de la página.
     * @param name Nombre del rol para filtrar (opcional).
     *             Si se proporciona, solo se devolverán roles que contengan este
     *             nombre.
     * @return Lista de roles como DTOs.
     */
    @GetMapping("/paged")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<RoleResponseDTO>> getAllRolesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Role> roles = roleService.findAllPagedAndFiltered(name, pageable);
        Page<RoleResponseDTO> dtos = roles.map(role -> modelMapper.map(role, RoleResponseDTO.class));
        return ResponseEntity.ok(dtos);
    }

    /**
     * Endpoint para crear un nuevo rol.
     * Solo accesible por usuarios con rol ADMIN.
     *
     * @param role Rol a crear.
     * @return Rol creado como DTO.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleResponseDTO> createRole(@RequestBody Role role) {
        Role savedRole = roleService.saveRole(role);
        RoleResponseDTO dto = modelMapper.map(savedRole, RoleResponseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    /**
     * Endpoint para actualizar un rol.
     * No se pueden actualizar los roles protegidos.
     * Solo accesible por usuarios con rol ADMIN.
     * 
     * @param id      ID del rol a actualizar.
     * @param roleDTO Datos del rol a actualizar.
     * @return Rol actualizado como DTO.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody RoleResponseDTO roleDTO) {
        try {
            Role updated = roleService.updateRole(id, roleDTO);
            RoleResponseDTO dto = modelMapper.map(updated, RoleResponseDTO.class);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint para eliminar un rol.
     * No se pueden borrar los roles protegidos.
     * 
     * @param id ID del rol a eliminar.
     * @return Respuesta HTTP sin contenido (204) si se eliminó correctamente,
     *         o 404 si no se encontró el rol, o 400 si hubo un error.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        try {
            roleService.deleteRole(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}