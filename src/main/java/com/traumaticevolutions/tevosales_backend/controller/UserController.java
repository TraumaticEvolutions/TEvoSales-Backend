package com.traumaticevolutions.tevosales_backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traumaticevolutions.tevosales_backend.model.Role;
import com.traumaticevolutions.tevosales_backend.model.User;
import com.traumaticevolutions.tevosales_backend.service.RoleService;
import com.traumaticevolutions.tevosales_backend.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



/**
 * Controlador REST para la gestión de usuarios.
 * Expone endpoints para el registro y consulta de los usuarios.
 * @author Ángel Aragón
 */

 @RestController
 @RequestMapping("/api/users")
 @RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    /**
     * Endpoint para registrar nuevo usuario.
     * @param user Usuario a registrar.
     * @return Usuario registrado.
     */
    @PostMapping("/register")
    public ResponseEntity<User> postMethodName(@RequestBody User user) {
        Optional<Role> role = roleService.findByName("CLIENTE");

        if (role.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        user.getRoles().add(role.get());
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    /**
     * Endpoint para obtener el listado de todos los usuarios.
     * @return Lista de usuarios.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

     /**
     * Endpoint para buscar un usuario por username.
     * @param username Username del usuario.
     * @return Usuario encontrado.
     */
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para buscar un usuario por nif.
     * @param nif Nif del usuario.
     * @return Usuario encontrado.
     */
    @GetMapping("/{nif}")
    public ResponseEntity<User> getUserByNif(@PathVariable String nif) {
        Optional<User> user = userService.findByNif(nif);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para buscar un usuario por email.
     * @param email Email del usuario.
     * @return Usuario encontrado.
     */
    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    

}
