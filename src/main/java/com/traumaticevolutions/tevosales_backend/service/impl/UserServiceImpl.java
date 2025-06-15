package com.traumaticevolutions.tevosales_backend.service.impl;

import com.traumaticevolutions.tevosales_backend.model.Role;
import com.traumaticevolutions.tevosales_backend.model.User;
import com.traumaticevolutions.tevosales_backend.repository.UserRepository;
import com.traumaticevolutions.tevosales_backend.repository.RoleRepository;
import com.traumaticevolutions.tevosales_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Implementación del servicio {@link UserService}.
 * Contiene la lógica de negocio para la gestión de usuarios,
 * incluyendo encriptación de contraseñas y consultas personalizadas.
 * 
 * Esta clase se inyecta automáticamente en los controladores mediante Service.
 * Utiliza Lombok con {@link RequiredArgsConstructor} para la inyección de
 * dependencias.
 * 
 * @author Ángel Aragón
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    /**
     * Guarda un nuevo usuario con su contraseña encriptada.
     * 
     * @param user Usuario a registrar.
     * @return Usuario guardado en base de datos.
     */
    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Busca un usuario por su nombre de usuario.
     * 
     * @param username Nombre de usuario.
     * @return Optional con el usuario encontrado, si existe.
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Busca un usuario por su ID.
     * 
     * @param id ID del usuario.
     * @return Optional con el usuario encontrado, si existe.
     */
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Busca un usuario por su dirección de correo electrónico.
     * 
     * @param email Email del usuario.
     * @return Optional con el usuario encontrado, si existe.
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Busca un usuario por su NIF.
     * 
     * @param nif Número de identificación fiscal.
     * @return Optional con el usuario encontrado, si existe.
     */
    @Override
    public Optional<User> findByNif(String nif) {
        return userRepository.findByNif(nif);
    }

    /**
     * Obtiene una lista de todos los usuarios registrados.
     * 
     * @return Lista de usuarios.
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Busca usuarios con filtros aplicados a nombre de usuario, email, NIF y rol.
     * 
     * @param username Nombre de usuario.
     * @param email    Email del usuario.
     * @param nif      Número de identificación fiscal.
     * @param role     Rol del usuario.
     * @param pageable Objeto Pageable con información de paginación.
     * @return Página de usuarios que coinciden con los filtros.
     */
    @Override
    public Page<User> findUsersWithFilters(String username, String email, String nif, String role, Pageable pageable) {
        Specification<User> spec = Specification.where(null);

        if (username != null && !username.isBlank()) {
            spec = spec.and(
                    (root, query, cb) -> cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase() + "%"));
        }
        if (email != null && !email.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }
        if (nif != null && !nif.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("nif")), "%" + nif.toLowerCase() + "%"));
        }
        if (role != null && !role.isBlank()) {
            spec = spec.and((root, query, cb) -> {
                Join<User, Role> join = root.join("roles", JoinType.LEFT);
                return cb.equal(join.get("name"), role);
            });
        }

        return userRepository.findAll(spec, pageable);
    }

    /**
     * Actualiza los roles de un usuario.
     * Asegura que el usuario siempre tenga el rol CLIENTE y, si es el usuario
     * Admin,
     * también el rol ADMIN.
     * 
     * @param userId ID del usuario a actualizar.
     * @param roles  Lista de nombres de roles a asignar.
     * @return Usuario actualizado.
     */
    @Override
    public User updateUserRoles(Long userId, List<String> roles) {
        boolean hasRoleCliente = false;
        boolean hasRoleAdmin = false;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        Set<Role> newRoles = new HashSet<>();
        for (String roleName : roles) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + roleName));
            newRoles.add(role);
            if (roleName.equals("ROLE_CLIENTE")) {
                hasRoleCliente = true;
            }
            if (roleName.equals("ROLE_ADMIN")) {
                hasRoleAdmin = true;
            }
        }
        if (!hasRoleCliente) {
            Role clienteRole = roleRepository.findByName("ROLE_CLIENTE")
                    .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: ROLE_CLIENTE"));
            newRoles.add(clienteRole);
        }
        if (user.getUsername().equals("admin") && !hasRoleAdmin) {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: ROLE_ADMIN"));
            newRoles.add(adminRole);
        }
        user.setRoles(newRoles);
        return userRepository.save(user);
    }

    /**
     * Elimina un usuario por su ID.
     * No se permite eliminar al usuario Admin.
     * 
     * @param userId ID del usuario a eliminar.
     * @return true si se eliminó correctamente, false si no se pudo eliminar (ej:
     *         usuario Admin).
     */
    @Override
    public boolean deleteUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if ("admin".equals(user.getUsername())) {
                return false;
            }
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
