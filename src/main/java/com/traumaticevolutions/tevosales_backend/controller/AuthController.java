package com.traumaticevolutions.tevosales_backend.controller;

import com.traumaticevolutions.tevosales_backend.model.User;
import com.traumaticevolutions.tevosales_backend.security.JwtService;
import com.traumaticevolutions.tevosales_backend.security.dto.AuthRequest;
import com.traumaticevolutions.tevosales_backend.security.dto.AuthResponse;
import com.traumaticevolutions.tevosales_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controlador REST encargado de la autenticación de usuarios.
 * Valida las credenciales y devuelve un token JWT si son correctas.
 * 
 * @author Ángel Aragón
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Endpoint de login. Valida el usuario y la contraseña,
     * y devuelve un token JWT si las credenciales son válidas.
     * 
     * @param request Datos de login (username y password)
     * @return Token JWT si autenticación correcta, o 401 si falla
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Optional<User> user = userService.findByUsername(request.getUsername());

        if (user.isEmpty() || !passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
            return ResponseEntity.status(401).build();
        }

        String token = jwtService.generateToken(user.get().getUsername(),
                user.get().getRoles().stream().map(role -> role.getName()).toList());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
